package com.evothings.mhand.presentation.feature.shared.header.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun BackButton(
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .background(
                color = colorScheme.secondary.copy(0.05f),
                shape = MegahandShapes.medium
            )
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_left),
            contentDescription = "chevronLeft",
            tint = colorScheme.secondary,
            modifier = Modifier
                .padding(MaterialTheme.paddings.large)
        )
    }

}