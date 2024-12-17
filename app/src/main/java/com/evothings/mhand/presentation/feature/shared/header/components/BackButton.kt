package com.evothings.mhand.presentation.feature.shared.header.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.evothings.mhand.presentation.theme.paddings

@Composable
fun BackButton(
    icon: ImageVector,
) {

    Box(
        modifier = Modifier
            .background(
                color = colorScheme.secondary.copy(0.05f),
                shape = shapes.medium
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "chevronLeft",
            modifier = Modifier
                .padding(MaterialTheme.paddings.large)
        )
    }

}