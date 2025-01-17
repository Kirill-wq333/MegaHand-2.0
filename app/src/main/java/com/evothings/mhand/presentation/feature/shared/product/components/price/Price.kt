package com.evothings.mhand.presentation.feature.shared.product.components.price

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.text.util.NumberSeparator
import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds
import com.evothings.mhand.presentation.theme.MegahandTheme

@Composable
fun Price(
    value: Double,
    cashback: Double,
    size: PriceSize = PriceSize.Small
) {

    val fontStyle =
        if (size == PriceSize.Small) typography.headlineSmall else typography.headlineMedium

    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${value.splitHundreds(NumberSeparator.SPACE)} ₽",
            style = fontStyle,
            color = colorScheme.secondary
        )
        if (cashback > 0) {
            CashbackPoints(
                value = cashback,
                size = size
            )
        }
    }
}

@Composable
fun CashbackPoints(
    value: Double,
    size: PriceSize
) {

    val textStyle =
        if (size == PriceSize.Small) typography.bodyMedium else typography.bodyLarge

    val prizeIconSize = if (size == PriceSize.Small) 15.dp else 20.dp

    Box(
        modifier = Modifier
            .background(
                color = colorScheme.inverseSurface.copy(0.1f),
                shape = MaterialTheme.shapes.extraLarge
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(
                    vertical = 1.5.dp,
                    horizontal = 3.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.tiny),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_prize),
                tint = colorScheme.inverseSurface,
                contentDescription = null,
                modifier = Modifier.size(prizeIconSize)
            )
            Text(
                text = "${value.toInt().splitHundreds(NumberSeparator.SPACE)} ₽",
                style = textStyle
            )
        }
    }
}

@Preview
@Composable
private fun PricePreview() {
    MegahandTheme {
        Surface {
            Price(
                value = 12110.54,
                cashback = 556.0
            )
        }
    }
}