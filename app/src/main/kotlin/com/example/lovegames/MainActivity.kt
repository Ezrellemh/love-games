package com.example.lovegames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.lovegames.compose.MainScreen
import com.example.lovegames.compose.NavigationComponent
import com.example.lovegames.ui.theme.LoveGamesTheme
import com.google.android.gms.ads.MobileAds
import com.lovegames.common.ads.InterstitialAdManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the AdMob SDK and start loading the first interstitial
        // so it's ready by the time the user reaches a More Games button.
        MobileAds.initialize(this) { /* init complete — no-op */ }
        InterstitialAdManager.preload(this)

        setContent {
            LoveGamesTheme {
                val navController = rememberNavController()
                NavigationComponent(navController)
            }
        }
    }
}


@Preview
@Composable
fun DefaultPreview() {
    LoveGamesTheme {
        MainScreen(navController = rememberNavController())
    }
}