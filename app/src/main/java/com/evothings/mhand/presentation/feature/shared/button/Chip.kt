package com.evothings.mhand.presentation.feature.shared.button

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun Chip(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {

    val borderColor =
        if (enabled) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondary.copy(0.2f)
        }

    Button(
        text = text,
        borderColor = borderColor,
        backgroundColor = Color.Transparent,
        onClick = onClick
    )
}