package com.evothings.widget.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.unit.FixedColorProvider
import com.evothings.widget.R

@Composable
fun ReloadIconButton(
    modifier: GlanceModifier,
    onClick: () -> Unit
) {
    Image(
        provider = ImageProvider(R.drawable.ic_refresh),
        colorFilter = ColorFilter.tint(
            FixedColorProvider(Color(0xFFB5B3B2))
        ),
        contentDescription = null,
        modifier = modifier.clickable(block = onClick)
    )
}