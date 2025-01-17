package com.evothings.mhand.presentation.feature.shared.checkbox

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens

@Preview
@Composable
private fun CheckboxPreview() {
    MegahandTheme(false) {
        val isChecked = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Checkbox(
                title = "Test title",
                isChecked = isChecked.value,
                onCheck = { isChecked.value = !isChecked.value }
            )
        }
    }
}

@Composable
fun Checkbox(
    modifier: Modifier = Modifier,
    title: String,
    isChecked: Boolean,
    onCheck: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onCheck
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        CheckboxChecker(
            isChecked = isChecked,
        )
        Text(
            text = title,
            style = typography.bodyLarge
        )
    }
}

@Composable
fun CheckboxChecker(
    modifier: Modifier = Modifier,
    isChecked: Boolean
) {
    Canvas(
        modifier = modifier.size(15.dp)
    ) {
        if (isChecked) {
            drawRoundRect(
                color = ColorTokens.Sunflower,
                cornerRadius = CornerRadius(6f)
            )
            drawRoundRect(
                color = ColorTokens.Graphite,
                topLeft = center / 2f,
                size = this.size / 2f,
                cornerRadius = CornerRadius(3f)
            )
        } else {
            drawRoundRect(
                color = Color(0x33262626),
                cornerRadius = CornerRadius(6f),
                style = Stroke(width = 1f)
            )
        }
    }
}