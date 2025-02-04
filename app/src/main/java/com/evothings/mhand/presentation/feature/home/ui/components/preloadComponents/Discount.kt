package com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextDecoration
import com.evothings.mhand.presentation.feature.shared.text.util.NumberSeparator
import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun Discount(
    enabled: Boolean,
    discount: Double,
    discountPercent: Double,
    isDiscountPercent: Boolean,
) {
    val discountChar = if (isDiscountPercent) '%' else '₽'

    Row(
        modifier = Modifier
            .alpha(if (enabled) 1.0f else 0.0f)
    ) {

        Text(
            text = discount.splitHundreds(NumberSeparator.SPACE),
            color = colorScheme.secondary.copy(0.6f),
            textDecoration = TextDecoration.LineThrough,
            style = MegahandTypography.bodyMedium
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.small))
        Text(
            text = "-${discountPercent.splitHundreds(NumberSeparator.SPACE)} $discountChar",
            color = colorScheme.error,
            style = MegahandTypography.bodyMedium
        )

    }
}
