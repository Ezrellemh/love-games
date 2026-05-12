package com.lovegames.thirtysixforlove.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lovegames.thirtysixforlove.R
import com.lovegames.thirtysixforlove.ui.ThirtySixQuestionsTheme


@Composable
fun ThirtySixQuestionsInstructionsScreen(navController: NavController) {
    ThirtySixQuestionsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.thirty_six_questions_instructions),
                style = MaterialTheme.typography.titleMedium.copy(
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.2.sp,
                ),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate("thirty_six_questions_screen") },
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 14.dp),
            ) {
                Text(
                    text = stringResource(R.string.sounds_good),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp,
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThirtySixQuestionsInstructionsScreenPreview() {
    ThirtySixQuestionsInstructionsScreen(navController = rememberNavController())
}