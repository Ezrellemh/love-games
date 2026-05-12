package com.lovegames.thirtysixforlove.compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.lovegames.common.MoreGamesButton
import com.lovegames.thirtysixforlove.ThirtySixQuestionsViewModel
import com.lovegames.thirtysixforlove.R
import com.lovegames.thirtysixforlove.ui.ThirtySixQuestionsTheme

@Composable
fun ThirtySixQuestionsTheyMightKissScreen(
    viewModel: ThirtySixQuestionsViewModel,
    navController: NavController
) {
    ThirtySixQuestionsTheme {
        ThirtySixQuestionsTheyMightKissScreenContent(viewModel, navController)
    }
}

@Composable
private fun ThirtySixQuestionsTheyMightKissScreenContent(
    viewModel: ThirtySixQuestionsViewModel,
    navController: NavController,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.thirty_six_questions_they_might_kiss),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = FontFamily.Cursive,
            ),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        val theyMightKissText = buildAnnotatedString {
            append(stringResource(R.string.thirty_six_questions_can_see))
            append(" ")
            pushStringAnnotation(tag = "URL", annotation = "https://youtu.be/Tvne48F0Eqw?t=314")
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append(stringResource(R.string.thirty_six_questions_here))
            }
            pop()
            append(" ")
            append(stringResource(R.string.thirty_six_questions_maybe))
        }

        ClickableText(
            modifier = Modifier.padding(16.dp),
            text = theyMightKissText,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 26.sp,
                letterSpacing = 0.2.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            ),
            onClick = { offset ->
                theyMightKissText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                        context.startActivity(intent)
                    }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Shared More Games CTA — shows an interstitial ad, then
        // resets this game's state and returns to the home screen.
        MoreGamesButton(
            onAdClosed = {
                viewModel.resetViewModel()
                navController.navigate("main_screen")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ThirtySixQuestionsTheyMightKissScreenPreview() {
    ThirtySixQuestionsTheyMightKissScreen(
        viewModel = ThirtySixQuestionsViewModel(),
        navController = NavController(LocalContext.current)
    )
}