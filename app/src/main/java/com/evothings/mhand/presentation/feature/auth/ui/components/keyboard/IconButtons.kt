package com.evothings.mhand.presentation.feature.auth.ui.components.keyboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R

@Composable
private fun Wrapper(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        content.invoke()
    }
}

@Composable
fun ExitTextButton(onClick: () -> Unit) {
    Wrapper(
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.exit),
            color = Color(0xFFB5B3B2), // TODO Replace with subdued40
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun DeleteIconButton(onClick: () -> Unit) {
    Wrapper(
        onClick = onClick
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_backspace),
            contentDescription = null,
            tint = Color(0xFFB5B3B2), // TODO Replace with subdued40
            modifier = Modifier
                .size(24.dp)
        )
    }
}