package com.lovegames.common

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lovegames.common.ads.InterstitialAdManager

/**
 * The standard "More Games" CTA used at the end of any love-game flow.
 *
 * Behavior:
 *   - Renders a Material3 button labeled "More Games".
 *   - On click, attempts to show a preloaded interstitial ad.
 *   - Once the ad is dismissed (or if no ad is available / it fails),
 *     [onAdClosed] fires. Wire that callback to do whatever transition
 *     the calling screen needs — typically resetting the game's
 *     ViewModel and navigating back to the games-list home screen.
 *
 * Any new love-game module can call [MoreGamesButton] at the end of its
 * flow to get the same ad-then-navigate behavior for free.
 *
 * Make sure to call [InterstitialAdManager.preload] once at app start
 * (e.g. from your top-level Activity) so an ad is ready by the time the
 * user reaches a More Games screen.
 */
@Composable
fun MoreGamesButton(
    onAdClosed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Button(
        modifier = modifier,
        onClick = {
            val activity = context.findActivity()
            if (activity != null) {
                InterstitialAdManager.show(activity, onClosed = onAdClosed)
            } else {
                // No host activity — should never happen in practice; degrade
                // gracefully by just running the post-ad action.
                onAdClosed()
            }
        }
    ) {
        Text(text = stringResource(R.string.more_games))
    }
}

/** Walks the ContextWrapper chain to find the hosting Activity, if any. */
internal fun Context.findActivity(): Activity? {
    var ctx: Context? = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}
