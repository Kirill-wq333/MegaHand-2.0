package com.evothings.mhand.presentation.feature.auth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.ui.draw.alpha

@Composable
fun SecureCodeDashes(
    codeLength: Int,
    errorState: Boolean,
    isActive: Boolean = true
) {

    Row(
        modifier = Modifier
            .alpha(
                alpha = if (isActive) 1.0f else 0.2f
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(4) { i ->
            val enabled = (i in 0 until codeLength)
            Dash(
                errorState = errorState,
                enabled = enabled
            )
        }
    }

}

@Composable
private fun Dash(
    errorState: Boolean,
    enabled: Boolean
) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .background(
                color = when {
                    errorState -> colorScheme.error
                    enabled -> colorScheme.primary
                    else -> colorScheme.secondary.copy(0.2f)
                },
                shape = shapes.small
            )
    )
}