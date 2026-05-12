package com.lovegames.thirtysixforlove.compose

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.MaterialTheme
import com.lovegames.common.MoreGamesButton
import com.lovegames.thirtysixforlove.ThirtySixQuestionsViewModel
import com.lovegames.thirtysixforlove.TimerCompletionAction
import com.lovegames.thirtysixforlove.ui.ThirtySixQuestionsState
import com.lovegames.thirtysixforlove.ui.ThirtySixQuestionsTheme
import com.lovegames.thirtysixforlove.R

@Composable
fun ThirtySixQuestionsCongratulationsScreen(
    viewModel: ThirtySixQuestionsViewModel,
    navController: NavController
) {
    ThirtySixQuestionsTheme {
        val state = viewModel.state().collectAsState().value
        when (state) {
            is ThirtySixQuestionsState.Content -> {
                ThirtySixQuestionsCongratulationsScreenContent(
                    viewModel,
                    navController,
                    state
                )
            }
        }
    }
}

@Composable
private fun ThirtySixQuestionsCongratulationsScreenContent(
    viewModel: ThirtySixQuestionsViewModel,
    navController: NavController,
    state: ThirtySixQuestionsState.Content,
) {
    val context = LocalContext.current
    val action = when {
        state.timerCompleted -> TimerCompletionAction.PlaySound
        else -> TimerCompletionAction.DoNothing
    }

    // Remember if the sound has been played, and preserve it across navigation
    if (state.timerCompleted && !state.hasPlayedSound) {
        val mediaPlayer = remember { MediaPlayer.create(context, R.raw.harp_strum) }
        DisposableEffect(Unit) {
            mediaPlayer.start()
            onDispose { mediaPlayer.release() }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                // Observe at the Initial pass so we toggle fast-forward without
                // consuming the touch — the timer heart / buttons still react
                // to their own taps. While any pointer is down, the stare timer
                // runs at 2x speed.
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        viewModel.setFastForward(event.changes.any { it.pressed })
                    }
                }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.thirty_six_questions_congratulations),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.stare),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Timer button with heart effects aka animations
            Timer(
                viewModel = viewModel,
                handleColor = MaterialTheme.colorScheme.primary,
                inactiveBarColor = MaterialTheme.colorScheme.primary,
                activeBarColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(200.dp),
                action = action
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        Column(
            modifier = Modifier.alpha(if (state.timerCompleted) 1f else 0f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    if (state.timerCompleted) navController.navigate("thirty_six_questions_they_might_kiss_screen")
                }
            ) {
                Text(text = stringResource(R.string.thirty_six_questions_adventurous))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Shared More Games CTA — shows an interstitial ad, then
            // resets this game's state and returns to the home screen.
            MoreGamesButton(
                onAdClosed = {
                    if (state.timerCompleted) {
                        viewModel.resetViewModel()
                        navController.navigate("main_screen")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThirtySixQuestionsCongratulationsScreenPreview() {
    ThirtySixQuestionsCongratulationsScreen(
        viewModel = ThirtySixQuestionsViewModel(),
        navController = NavController(LocalContext.current)
    )
}