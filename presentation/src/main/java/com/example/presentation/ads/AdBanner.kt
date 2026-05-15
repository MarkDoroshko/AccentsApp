package com.example.presentation.ads

import android.content.Context
import android.util.DisplayMetrics
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData

@Composable
fun AdBanner(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val bannerView = remember {
        BannerAdView(context).apply {
            setAdSize(inlineSize(context))
            setBannerAdEventListener(object : BannerAdEventListener {
                override fun onAdLoaded() {}
                override fun onAdFailedToLoad(error: AdRequestError) {}
                override fun onAdClicked() {}
                override fun onImpression(impressionData: ImpressionData?) {}
            })
            loadAd(AdRequest.Builder(AdsConfig.BANNER_AD_UNIT_ID).build())
        }
    }

    DisposableEffect(bannerView) {
        onDispose { bannerView.destroy() }
    }

    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { bannerView }
    )
}

private const val INLINE_BANNER_MAX_HEIGHT_DP = 90

private fun inlineSize(context: Context): BannerAdSize {
    val metrics: DisplayMetrics = context.resources.displayMetrics
    val widthDp = (metrics.widthPixels / metrics.density).toInt()
    return BannerAdSize.inline(context, widthDp, INLINE_BANNER_MAX_HEIGHT_DP)
}
