package com.evothings.mhand.presentation.feature.cart.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.product.components.price.CashbackPoints
import com.evothings.mhand.presentation.feature.shared.product.components.price.PriceSize
import com.evothings.mhand.presentation.feature.shared.text.util.NumberSeparator
import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun Checkout(
    modifier: Modifier = Modifier,
    productsCount: Int,
    total: Double,
    discount: Double,
    deliveryCost: Double = 0.0,
    cashbackPoints: Double,
    pointsDiscount: Double = 0.0,
    summary: Double,
){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.paddings.giant,
                    horizontal = MaterialTheme.paddings.extraGiant
                )
        ) {
            CheckoutItem(
                value = "${total.splitHundreds(NumberSeparator.SPACE)} ₽",
                text = stringResource(R.string.products_count, productsCount),
                color = colorScheme.secondary
            )
            if (discount > 0) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
                CheckoutItem(
                    value = "${discount.splitHundreds(NumberSeparator.SPACE)} ₽",
                    text = stringResource(R.string.discount),
                    color = colorScheme.error
                )
            }
            if (pointsDiscount > 0){
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
                CheckoutItem(
                    value = "-${discount.splitHundreds(NumberSeparator.SPACE)} ₽",
                    text = stringResource(R.string.checkout_points_discount),
                    color = colorScheme.inverseSurface
                )
            }
            if (deliveryCost > 0) {
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
                CheckoutItem(
                    value = "${deliveryCost.splitHundreds(NumberSeparator.SPACE)} ₽",
                    text = stringResource(R.string.delivery_checkout_item),
                    color = colorScheme.secondary
                )
            }
            if (cashbackPoints > 0) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
                Cashback(
                    text = stringResource(R.string.cashback),
                    money = cashbackPoints
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.total),
                    color = colorScheme.secondary,
                    style = MegahandTypography.headlineMedium
                )
                Text(
                    text = "${summary.splitHundreds(NumberSeparator.SPACE)} ₽",
                    color = colorScheme.secondary,
                    style = MegahandTypography.headlineMedium
                )
            }

        }

}





@Composable
private fun CheckoutItem(
    value: String,
    text: String,
    color: Color
){

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge
        )
        Text(
            text = value,
            color = color,
            style = MegahandTypography.bodyLarge
        )
    }

}

@Composable
fun Cashback(
    text: String,
    money: Double,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge
        )
        CashbackPoints(
            value = money,
            size = PriceSize.Big
        )
    }
}