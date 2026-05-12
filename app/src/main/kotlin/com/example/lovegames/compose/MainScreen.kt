package com.example.lovegames.compose

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lovegames.R
import com.lovegames.thirtysixforlove.ui.ThirtySixQuestionsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Home screen — the launching pad for all love games. Each game's entry
 * point is its own card, themed with that game's color palette so the
 * card visually previews the experience inside.
 */
@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Love Games",
            style = MaterialTheme.typography.displayMedium.copy(
                fontFamily = FontFamily.Cursive,
            ),
            color = Color(0xFFE53935),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Pick a game",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
        )

        Spacer(modifier = Modifier.height(56.dp))

        // The 36 Questions entry. Each game card is scoped inside its own
        // theme so the card preview uses that game's palette without
        // affecting the surrounding home screen.
        ThirtySixQuestionsTheme(applyBackground = false) {
            GameCard(
                title = stringResource(R.string.thirty_six_questions_to_fall_in_love),
                onNavigate = {
                    navController.navigate("thirty_six_questions_disclaimer_screen")
                },
            )
        }
    }
}

/**
 * Generic game-entry card with a press-bounce animation: the card scales
 * down on press, springs back up, then triggers [onNavigate] once the
 * motion is mostly complete. Reused as more game cards are added.
 */
@Composable
private fun GameCard(
    title: String,
    onNavigate: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var pressed by remember { mutableStateOf(false) }
    var navigating by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.94f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        label = "game-card-press-scale",
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(scaleX = scale, scaleY = scale),
        enabled = !navigating,
        onClick = {
            if (navigating) return@Card
            navigating = true
            pressed = true
            // Squeeze for a moment, release, then navigate once the
            // bounce-back is well underway.
            scope.launch {
                delay(180)
                pressed = false
                delay(280)
                onNavigate()
            }
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 28.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = FontFamily.Cursive,
                ),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(navController = androidx.navigation.compose.rememberNavController())
}
