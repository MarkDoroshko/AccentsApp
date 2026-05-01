package com.example.accentsapp

import android.app.Application
import android.util.Log
import com.yandex.mobile.ads.common.YandexAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        YandexAds.initialize(this) {
            Log.d("YandexAds", "SDK initialized")
        }
    }
}