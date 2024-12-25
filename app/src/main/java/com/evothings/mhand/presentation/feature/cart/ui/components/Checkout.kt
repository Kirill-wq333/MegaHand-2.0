package com.evothings.mhand.presentation.feature.cart.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.header.ui.components.PrizeAndMoney
import com.evothings.mhand.presentation.feature.shared.text.util.NumberSeparator
import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun Checkout(
    productsCount: Int,
    total: Double,
    discount: Double,
    cashbackPoints: Double,
    summary: Double,
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
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
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
            if (discount > 0) {
                CheckoutItem(
                    value = "${discount.splitHundreds(NumberSeparator.SPACE)} ₽",
                    text = stringResource(R.string.discount),
                    color = colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            if (cashbackPoints > 0) {
                Cashback(
                    text = stringResource(R.string.cashback),
                    selected = true,
                    money = "${cashbackPoints.splitHundreds(NumberSeparator.SPACE)} ₽"
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

}





@Composable
fun CheckoutItem(
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
    money: String,
    selected: Boolean
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
        PrizeAndMoney(
            selected = selected,
            money = money,
        )
    }
}