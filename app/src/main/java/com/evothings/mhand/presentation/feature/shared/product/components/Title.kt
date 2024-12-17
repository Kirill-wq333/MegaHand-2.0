package com.evothings.mhand.presentation.feature.shared.product.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ProductTitle(
    value: String,
    maxLines: Int = 2
) {
    Text(
        text = value,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.secondary.copy(0.6f),
    )
}