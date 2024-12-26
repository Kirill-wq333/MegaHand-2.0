package com.evothings.mhand.presentation.feature.shared

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.presentation.theme.MegahandTheme

@Preview
@Composable
private fun ChipPreview() {
    MegahandTheme {
        Surface {
            Chip(
                text = "Some",
                isEnabled = true,
                onClick = {}
            )
        }
    }
}

@Deprecated(
    message = "Use new chip",
    replaceWith = ReplaceWith(
        "Chip",
        "com.evothings.mhand.feature.shared.button.Chip"
    )
)
@Composable
fun Chip(
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {

    val borderColor =
        if (isEnabled) colorScheme.primary else colorScheme.secondary.copy(0.2f)

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shapes.medium
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp),
            text = text,
            style = typography.headlineSmall
        )
    }

}