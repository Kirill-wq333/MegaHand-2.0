package com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun Information(
    price: Double,
    cashback: Double,
    discount: Double,
    valueDiscount: Double,
    keepOldPrice: Boolean,
    discountPercent: Double,
    isDiscountPercent: Boolean,
    title: String,
    condition: String,
    size: String,
    showSizeAndCondition: Boolean
){

    Price(price = price, cashback = cashback)
    Spacer(modifier = Modifier.height(MaterialTheme.spacers.tiny))
    if (keepOldPrice || discount > 0) {
        Discount(
            discount = valueDiscount,
            discountPercent = discountPercent,
            isDiscountPercent = isDiscountPercent,
            enabled = (discount > 0)
        )
    }
    Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
    Text(
        text = title,
        color = colorScheme.secondary.copy(0.6f),
        style = MegahandTypography.bodyLarge,
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.paddings.extraLarge
            )
    )
    Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
    if (showSizeAndCondition) {
        SizeAndStars(estimation = condition, textSize = size)
    }
}