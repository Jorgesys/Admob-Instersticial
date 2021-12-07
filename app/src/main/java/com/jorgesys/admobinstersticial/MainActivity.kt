package com.jorgesys.admobinstersticial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.*

class MainActivity : AppCompatActivity() {

    //https://developers.google.com/admob/android/interstitial
    //https://developers.google.com/admob/android/quick-start

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}

        var adRequest = AdRequest.Builder().build()

        //val requestConfiguration = RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("A1C61A6757E98B5F7C82790164D94638"))
        val testDeviceIds = Arrays.asList("A1C61A6757E98B5F7C82790164D94638")
        val requestConfiguration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(requestConfiguration)

        //Demo ad units https://developers.google.com/admob/android/test-ads#demo_ad_units
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712"/*"ca-app-pub-3940256099942544/1033173712" Google testing id*/, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, "onAdFailedToLoad() " +  adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd

                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this@MainActivity)
                } else {
                    Log.e(TAG, "The interstitial ad wasn't ready yet.")
                }

                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        Log.i(TAG, "interstitial dismissed. ")
                        startActivity(Intent(applicationContext, ResultActivity::class.java))
                    }
                }


            }


        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null
            }

        }





    }
}