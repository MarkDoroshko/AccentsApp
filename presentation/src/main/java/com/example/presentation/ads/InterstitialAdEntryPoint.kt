package com.example.presentation.ads

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface InterstitialAdEntryPoint {
    fun interstitialAdManager(): InterstitialAdManager
}
