package com.lovegames.thirtysixforlove.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// Palette derived from the Red/Magenta accents already in use throughout
// the 36 Questions experience. Pure Color.Red and Color.Magenta drive the
// primary/secondary roles so existing usages remain visually identical
// when migrated to `MaterialTheme.colorScheme.*`. Supporting tones are
// soft pinks/blushes for backgrounds, containers, and surfaces.

private val LoveRed = Color.Red                       // FF0000 — primary accent
private val LoveMagenta = Color.Magenta               // FF00FF — secondary accent
private val DeepRed = Color(0xFF8B0000)               // "on primary container" text
private val DeepMagenta = Color(0xFF6A006A)           // "on secondary container" text
private val SoftPink = Color(0xFFFFD6E0)              // primary container
private val SoftMagentaPink = Color(0xFFFFD6FA)       // secondary container
private val Blush = Color(0xFFFFF5F8)                 // page background
private val Cream = Color(0xFFFFFAFC)                 // raised surface
private val DeepInkText = Color(0xFF2A0010)           // body text on light surfaces
private val MutedRose = Color(0xFFE091A6)             // outline

private val ThirtySixColors = lightColorScheme(
    primary = LoveRed,
    onPrimary = Color.White,
    primaryContainer = SoftPink,
    onPrimaryContainer = DeepRed,

    secondary = LoveMagenta,
    onSecondary = Color.White,
    secondaryContainer = SoftMagentaPink,
    onSecondaryContainer = DeepMagenta,

    tertiary = Color(0xFFFF80B0),
    onTertiary = Color.White,

    background = Blush,
    onBackground = DeepInkText,

    surface = Cream,
    onSurface = DeepInkText,

    surfaceVariant = Color(0xFFFFF0F4),
    onSurfaceVariant = Color(0xFF5C2A36),

    outline = MutedRose,
)

/**
 * Themed wrapper for any screen in the 36 Questions experience.
 *
 * Provides the love-game color palette: red/magenta primary, soft-pink
 * containers, blush background. By default also paints a full-screen
 * [Surface] tinted with the theme's background color — pass
 * [applyBackground] = false when using the theme to scope-color a smaller
 * subtree (e.g. a single Card embedded in another module's screen) where
 * you don't want it to take over the whole layout.
 */
@Composable
fun ThirtySixQuestionsTheme(
    applyBackground: Boolean = true,
    content: @Composable () -> Unit,
) {
    MaterialTheme(colorScheme = ThirtySixColors) {
        if (applyBackground) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
                content = content,
            )
        } else {
            content()
        }
    }
}
