package com.lovegames.common.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

/**
 * A small, app-wide holder for a single preloaded interstitial.
 *
 * Lifecycle:
 *   1. Call [preload] once at app start (e.g. from MainActivity.onCreate).
 *   2. Call [show] when you want to display an ad before some transition —
 *      the [onClosed] callback fires when the user dismisses the ad.
 *   3. After each successful show, the manager auto-reloads the next ad.
 *
 * If no ad is loaded yet, or the ad fails to show, [onClosed] is invoked
 * immediately so the user's flow is never blocked by ad-load failures.
 *
 * Replace [TEST_AD_UNIT_ID] (or call [configure]) with your real AdMob ad
 * unit ID before releasing — the default is Google's official test unit.
 */
object InterstitialAdManager {
    private const val TAG = "InterstitialAdManager"

    // Google's official test interstitial ad unit. Safe to use in development;
    // serves a placeholder ad. Replace with your real ad unit ID via [configure]
    // (or by editing this file) before publishing.
    const val TEST_AD_UNIT_ID: String = "ca-app-pub-3940256099942544/1033173712"

    private var adUnitId: String = TEST_AD_UNIT_ID
    private var loadedAd: InterstitialAd? = null
    private var isLoading: Boolean = false

    /** Override the ad unit ID (call once before [preload]). */
    fun configure(adUnitId: String) {
        this.adUnitId = adUnitId
    }

    /** Begin loading the next interstitial. Safe to call multiple times. */
    fun preload(context: Context) {
        if (loadedAd != null || isLoading) return
        isLoading = true
        InterstitialAd.load(
            context.applicationContext,
            adUnitId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d(TAG, "Interstitial loaded")
                    loadedAd = ad
                    isLoading = false
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.w(TAG, "Interstitial failed to load: ${error.message}")
                    loadedAd = null
                    isLoading = false
                }
            }
        )
    }

    /**
     * Show the currently loaded interstitial, invoking [onClosed] when the
     * user dismisses it. If no ad is loaded or showing fails, [onClosed]
     * fires immediately and a fresh preload is started in the background.
     */
    fun show(activity: Activity, onClosed: () -> Unit) {
        val ad = loadedAd
        if (ad == null) {
            preload(activity)
            onClosed()
            return
        }
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                loadedAd = null
                preload(activity)
                onClosed()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                Log.w(TAG, "Interstitial failed to show: ${error.message}")
                loadedAd = null
                preload(activity)
                onClosed()
            }

            override fun onAdShowedFullScreenContent() {
                // Once shown, the ad object is consumed.
                loadedAd = null
            }
        }
        ad.show(activity)
    }
}
