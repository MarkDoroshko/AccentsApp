package com.example.presentation.ads

import android.app.Activity
import android.content.Context
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterstitialAdManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val loader: InterstitialAdLoader = InterstitialAdLoader(context)
    private var ad: InterstitialAd? = null
    private var loading: Boolean = false

    init {
        preload()
    }

    fun preload() {
        if (ad != null || loading) return
        loading = true
        loader.loadAd(
            AdRequest.Builder(AdsConfig.INTERSTITIAL_AD_UNIT_ID).build(),
            object : InterstitialAdLoadListener {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    ad = interstitialAd
                    loading = false
                }

                override fun onAdFailedToLoad(error: AdRequestError) {
                    ad = null
                    loading = false
                }
            }
        )
    }

    fun show(activity: Activity, onDismiss: () -> Unit) {
        val current = ad
        if (current == null) {
            onDismiss()
            preload()
            return
        }
        current.setAdEventListener(object : InterstitialAdEventListener {
            override fun onAdShown() {}
            override fun onAdFailedToShow(adError: AdError) {
                ad = null
                onDismiss()
                preload()
            }

            override fun onAdDismissed() {
                ad = null
                onDismiss()
                preload()
            }

            override fun onAdClicked() {}
            override fun onAdImpression(impressionData: ImpressionData?) {}
        })
        current.show(activity)
    }
}
