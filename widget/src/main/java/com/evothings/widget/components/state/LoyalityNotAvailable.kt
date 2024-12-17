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
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.FixedColorProvider
import com.evothings.widget.R

@Composable
fun LoyalityNotAvailable(
    modifier: GlanceModifier,
    vertical: Boolean,
    iconSize: Dp,
    textSize: TextUnit,
    gap: Dp
) {
    if (vertical) {
        Column(
            modifier = modifier,
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            CardIcon(
                size = iconSize
            )
            Spacer(
                modifier = GlanceModifier.height(gap)
            )
            LoyalityText(
                textSize = textSize,
                align = TextAlign.Center
            )
        }
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            CardIcon(
                size = iconSize
            )
            Spacer(
                modifier = GlanceModifier.width(gap)
            )
            LoyalityText(
                textSize = textSize,
                align = TextAlign.Center
            )
        }
    }
}

@Composable
private fun CardIcon(
    size: Dp
) {
    Image(
        provider = ImageProvider(R.drawable.ic_no_card),
        contentDescription = null,
        colorFilter = ColorFilter.tint(
            FixedColorProvider(Color(0xFFB5B3B2))
        ),
        modifier = GlanceModifier.size(size)
    )
}

@Composable
private fun LoyalityText(
    textSize: TextUnit,
    align: TextAlign
) {
    Text(
        text = "В вашем городе не работает система лояльности",
        style = TextStyle(
            color = FixedColorProvider(Color(0xFF46423E)),
            fontSize = textSize,
            fontWeight = FontWeight.Medium,
            textAlign = align
        )
    )
}