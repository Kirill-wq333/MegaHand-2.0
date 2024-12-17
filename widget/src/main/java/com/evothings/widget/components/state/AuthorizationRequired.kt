package com.evothings.widget.components.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import com.evothings.widget.R
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.FixedColorProvider

@Composable
internal fun AuthorizationRequired(
    modifier: GlanceModifier,
    vertical: Boolean,
    titleSize: TextUnit,
    subtitleSize: TextUnit,
    iconSize: Dp,
    textGap: Dp,
    elementsGap: Dp
) {
    if (vertical) {
        Vertical(
            modifier = modifier,
            titleSize = titleSize,
            subtitleSize = subtitleSize,
            iconSize = iconSize,
            textGap = textGap,
            elementsGap = elementsGap
        )
    } else {
        Horizontal(
            modifier = modifier,
            titleSize = titleSize,
            subtitleSize = subtitleSize,
            iconSize = iconSize,
            textGap = textGap,
            elementsGap = elementsGap
        )
    }
}

@Composable
private fun Vertical(
    modifier: GlanceModifier,
    titleSize: TextUnit,
    subtitleSize: TextUnit,
    iconSize: Dp,
    textGap: Dp,
    elementsGap: Dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
        verticalAlignment = Alignment.Vertical.CenterVertically
    ) {
        PersonIcon(
            size = iconSize
        )
        Spacer(
            modifier = GlanceModifier.height(elementsGap)
        )
        TextBlock(
            titleSize = titleSize,
            subtitleSize = subtitleSize,
            gap = textGap,
            align = TextAlign.Center
        )
    }
}

@Composable
private fun Horizontal(
    modifier: GlanceModifier,
    titleSize: TextUnit,
    subtitleSize: TextUnit,
    iconSize: Dp,
    textGap: Dp,
    elementsGap: Dp
) {
    Row(
        modifier = modifier,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
        verticalAlignment = Alignment.Vertical.CenterVertically
    ) {
        PersonIcon(
            size = iconSize
        )
        Spacer(
            modifier = GlanceModifier.width(elementsGap)
        )
        TextBlock(
            titleSize = titleSize,
            subtitleSize = subtitleSize,
            gap = textGap,
            align = TextAlign.Start
        )
    }
}

@Composable
private fun TextBlock(
    titleSize: TextUnit,
    subtitleSize: TextUnit,
    align: TextAlign,
    gap: Dp
) {
    Column(
        modifier = GlanceModifier,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
        verticalAlignment = Alignment.Vertical.CenterVertically
    ) {
        Text(
            modifier = GlanceModifier.fillMaxWidth(),
            text = "Необходима авторизация",
            style = TextStyle(
                color = FixedColorProvider(Color(0xFF46423E)),
                fontSize = titleSize,
                fontWeight = FontWeight.Bold,
                textAlign = align
            )
        )
        Spacer(
            modifier = GlanceModifier.height(gap)
        )
        Text(
            text = "Нажмите на виджет, чтобы авторизоваться",
            style = TextStyle(
                color = FixedColorProvider(Color(0xFF908E8B)),
                fontSize = subtitleSize,
                fontWeight = FontWeight.Medium,
                textAlign = align
            )
        )
    }
}

@Composable
private fun PersonIcon(
    size: Dp
) {
    Image(
        provider = ImageProvider(R.drawable.ic_profile),
        contentDescription = null,
        colorFilter = ColorFilter.tint(
            FixedColorProvider(Color(0xFFB5B3B2))
        ),
        modifier = GlanceModifier.size(size)
    )
}