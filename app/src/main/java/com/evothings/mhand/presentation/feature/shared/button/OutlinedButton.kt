package com.evothings.mhand.presentation.feature.shared.button

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import com.evothings.mhand.presentation.utils.ui.nonScaledSp

@Deprecated(message = "Replace with Button or SmallButton")
@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
) {

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = colorScheme.secondary.copy(0.2f),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 14.nonScaledSp,
            style = typography.titleMedium
        )
    }

}