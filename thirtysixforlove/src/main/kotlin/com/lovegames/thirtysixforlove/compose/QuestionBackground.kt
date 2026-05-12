package com.lovegames.thirtysixforlove.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.JoinInner
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * A subtle, low-alpha icon placed behind the question to evoke its theme.
 *
 * Designed so it never competes with the question text — single large icon,
 * low alpha, centered.
 */
@Composable
fun QuestionBackground(questionIndex: Int) {
    val icon = iconForQuestion(questionIndex)
    val tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.07f)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(280.dp),
        )
    }
}

/**
 * Maps each 36-question index (0-based) to a themed Material icon.
 * The mapping is deliberately gentle — no icon is meant to call attention
 * to itself; each one just hints at the topic.
 */
private fun iconForQuestion(questionIndex: Int): ImageVector = when (questionIndex) {
    // 1. Invite anyone to dinner
    0 -> Icons.Filled.Restaurant
    // 2. Would you like to be famous?
    1 -> Icons.Filled.Star
    // 3. Rehearse before phone calls?
    2 -> Icons.Outlined.Phone
    // 4. What is a perfect day?
    3 -> Icons.Filled.WbSunny
    // 5. When did you last sing?
    4 -> Icons.Filled.MusicNote
    // 6. Mind or body of 30 at age 90?
    5 -> Icons.Filled.HourglassEmpty
    // 7. Secret hunch about how you'll die?
    6 -> Icons.Filled.HelpOutline
    // 8. Things you and your partner have in common
    7 -> Icons.Filled.JoinInner
    // 9. What are you most grateful for?
    8 -> Icons.Filled.Favorite
    // 10. Change about how you were raised?
    9 -> Icons.Filled.Home
    // 11. Tell your life story in 4 minutes
    10 -> Icons.Filled.AutoStories
    // 12. Wake up with a new quality or ability
    11 -> Icons.Filled.Lightbulb
    // 13. If a crystal ball could tell you the truth
    12 -> Icons.Filled.Visibility
    // 14. Long-time dream
    13 -> Icons.Filled.Cloud
    // 15. Greatest accomplishment
    14 -> Icons.Filled.EmojiEvents
    // 16. What do you value in friendship?
    15 -> Icons.Filled.Handshake
    // 17. Most treasured memory
    16 -> Icons.Filled.PhotoCamera
    // 18. Most terrible memory (keep gentle)
    17 -> Icons.Filled.Cloud
    // 19. If you knew you'd die in a year
    18 -> Icons.Filled.CalendarToday
    // 20. What does friendship mean?
    19 -> Icons.Filled.People
    // 21. Roles of love and affection
    20 -> Icons.Filled.Favorite
    // 22. Positive characteristics of your partner
    21 -> Icons.Filled.ThumbUp
    // 23. How close and warm is your family?
    22 -> Icons.Filled.FamilyRestroom
    // 24. Relationship with your mother
    23 -> Icons.Filled.Face
    // 25. Three "we" statements
    24 -> Icons.Filled.People
    // 26. "I wish I had someone with whom I could share…"
    25 -> Icons.Filled.ChatBubbleOutline
    // 27. What's important for your partner to know?
    26 -> Icons.Filled.Info
    // 28. What you like about them (very honest)
    27 -> Icons.Filled.SentimentSatisfied
    // 29. Share an embarrassing moment
    28 -> Icons.Filled.SentimentDissatisfied
    // 30. When did you last cry?
    29 -> Icons.Filled.WaterDrop
    // 31. Something you like about them already
    30 -> Icons.Filled.Mood
    // 32. What's too serious to be joked about?
    31 -> Icons.Filled.Balance
    // 33. Regret not having told someone
    32 -> Icons.Filled.Mail
    // 34. House catches fire — what one item?
    33 -> Icons.Filled.LocalFireDepartment
    // 35. Family death (keep gentle — flower)
    34 -> Icons.Filled.LocalFlorist
    // 36. Share a personal problem, ask advice
    35 -> Icons.Filled.QuestionAnswer
    else -> Icons.Filled.Favorite
}
