package com.evothings.mhand.presentation.feature.shared.loading

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(28.dp),
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = 2.dp,
        trackColor = MaterialTheme.colorScheme.secondary.copy(0.6f)
    )
}