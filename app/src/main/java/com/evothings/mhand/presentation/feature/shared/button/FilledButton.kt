package com.evothings.mhand.presentation.feature.shared.button

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.utils.ui.nonScaledSp

@Deprecated(message = "Replace with Button or SmallButton")
@Composable
fun FilledButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    indication: Indication? = null,
    backgroundColor: Color = colorScheme.primary,
    textColor: Color = ColorTokens.Graphite,
    onClick: () -> Unit,
) {

    val buttonOpacity = remember(enabled) { if (enabled) 1.0f else 0.3f }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor.copy(
                    alpha = buttonOpacity
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = indication,
                onClick = { if (enabled) onClick() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = textColor.copy(alpha = buttonOpacity),
            fontSize = 14.nonScaledSp,
            style = typography.titleMedium
        )
    }
}