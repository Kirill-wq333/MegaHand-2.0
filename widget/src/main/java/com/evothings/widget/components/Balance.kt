package com.evothings.widget.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.size
import com.evothings.widget.R
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.FixedColorProvider
import kotlin.math.abs

@Composable
fun Balance(
    modifier: GlanceModifier = GlanceModifier,
    balance: Int,
    textSize: TextUnit,
    giftIconSize: Dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Vertical.CenterVertically
    ) {
        Image(
            provider = ImageProvider(R.drawable.ic_prize),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                colorProvider = FixedColorProvider(
                    color = Color(0xFFE7D52F)
                )
            ),
            modifier = GlanceModifier.size(giftIconSize)
        )
        Spacer(
            modifier = GlanceModifier.width(4.dp)
        )
        Text(
            text = "${balance.toString().separateHundreds()} ₽",
            style = TextStyle(
                color = FixedColorProvider(Color(0xFF46423E)),
                fontWeight = FontWeight.Bold,
                fontSize = textSize
            )
        )
    }
}

private fun String.separateHundreds(): String {
    if (this == "???" || this == "") return this

    val number = this.toInt()
    if (number < 100) return this
    val absNumber = abs(number).toLong()
    return when {
        absNumber >= 1_000_000 -> String.format("%,d", absNumber / 1_000_000) + ",${String.format("%03d", absNumber % 1_000_000 / 1_000)},${String.format("%03d", absNumber % 1_000)}"
        absNumber >= 1_000 -> String.format("%,d", absNumber / 1_000) + ",${String.format("%03d", absNumber % 1_000)}"
        else -> String.format("%,d", absNumber)
    }
}