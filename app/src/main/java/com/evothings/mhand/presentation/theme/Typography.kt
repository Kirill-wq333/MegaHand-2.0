package com.evothings.mhand.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R

private val golos = FontFamily(
    Font(R.font.golos_500, FontWeight.Medium),
    Font(R.font.golos_400, FontWeight.Normal)
)

@Stable
private fun getLetterSpacing(fontSize: Int): Double =
    if (fontSize >= 16) 0.018 else 0.011

val MegahandTypography: Typography
    @Composable
    get() = Typography(
        headlineLarge = TextStyle(
            fontSize = 36.sp,
            lineHeight = 36.sp,
            fontFamily = golos,
            fontWeight = FontWeight.Medium,
            letterSpacing = getLetterSpacing(36).sp
        ),
        headlineMedium = TextStyle(
            fontSize = 20.sp,
            lineHeight = 20.sp,
            fontFamily = golos,
            fontWeight = FontWeight.Medium,
            letterSpacing = getLetterSpacing(20).sp
        ),
        headlineSmall = TextStyle(
            fontSize = 18.sp,
            lineHeight = 19.8.sp,
            fontFamily = golos,
            fontWeight = FontWeight.Medium,
            letterSpacing = getLetterSpacing(18).sp
        ),
        labelLarge = TextStyle(
            fontSize = 16.sp,
            lineHeight = 19.84.sp,
            fontFamily = golos,
            fontWeight = FontWeight.Medium,
            letterSpacing = getLetterSpacing(16).sp
        ),
        bodyLarge = TextStyle(
            fontSize = 16.sp,
            lineHeight = 19.84.sp,
            fontFamily = golos,
            fontWeight = FontWeight.Normal,
            letterSpacing = getLetterSpacing(16).sp
        ),
        bodyMedium = TextStyle(
            fontSize = 12.sp,
            lineHeight = 14.88.sp,
            fontFamily = golos,
            fontWeight = FontWeight.Normal,
            letterSpacing = getLetterSpacing(12).sp
        ),
        bodySmall = TextStyle(
            fontSize = 10.sp,
            lineHeight = 11.7.sp,
            fontFamily = golos,
            fontWeight = FontWeight.Normal,
            letterSpacing = getLetterSpacing(10).sp
        )
    )