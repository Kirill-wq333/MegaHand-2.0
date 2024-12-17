package com.evothings.mhand.presentation.feature.shared.radio

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens

@Composable
fun RadioButton(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onSelect
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        RadioChecker(
            isChecked = isSelected
        )
        Text(
            text = title,
            style = typography.bodyLarge
        )
    }
}

@Composable
fun RadioChecker(
    isChecked: Boolean
) {

    val circleColor =
        colorScheme.secondary.copy(0.2f)

    Canvas(
        modifier = Modifier.size(14.dp)
    ) {
        if (isChecked) {
            drawCircle(
                color = ColorTokens.Sunflower,
            )
            drawCircle(
                color = Color(0xFF46423E),
                radius = size.minDimension / 4f
            )
        } else {
            drawCircle(
                color = circleColor,
                style = Stroke(width = 1f)
            )
        }
    }
}

@Preview
@Composable
private fun RadioButtonPreview() {
    MegahandTheme(false) {
        val isChecked = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            RadioButton(
                title = "Title",
                isSelected = isChecked.value,
                onSelect = { isChecked.value = !isChecked.value }
            )
        }
    }
}
