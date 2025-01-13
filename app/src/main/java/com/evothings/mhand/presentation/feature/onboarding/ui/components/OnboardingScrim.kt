package com.evothings.mhand.presentation.feature.onboarding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens


@Composable
fun OnboardingScrim(
    onClick: () -> Unit
) {

    val isDarkTheme = (MaterialTheme.colorScheme.secondary != ColorTokens.Graphite)
    val scrimColor = if (isDarkTheme) Color.Black else ColorTokens.Graphite

    Box(
        modifier = Modifier
            .alpha(0.4f)
            .fillMaxSize()
            .background(scrimColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    )

}