package com.evothings.mhand.presentation.feature.cart.ui.components.makingAnOrderComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Price
import com.evothings.mhand.presentation.feature.shared.checkbox.Checkbox
import com.evothings.mhand.presentation.feature.shared.loyalityCard.Balance
import com.evothings.mhand.presentation.feature.shared.loyalityCard.Cashback
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers


@Composable
fun SystemLoyality(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.paddings.extraGiant)
    ) {
        HeadingAndPoints()
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Balance(
                    balance = 100,
                    isOffline = false,
                    modifier = Modifier
                        .size(24.dp)
                )
                Cashback(
                    cashback = 5,
                    isOffline = false,
                    isSmallSize = true
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        Text(
            text = stringResource(R.string.bonus_withdraw_notice),
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        AmountAndSelectAll()
    }
}

@Composable
fun AmountAndSelectAll(
    modifier: Modifier = Modifier
) {

    var amount by remember { mutableStateOf("") }

    Column {
        Text(
            text = stringResource(R.string.amount),
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        MTextField(
            value = amount,
            placeholder = "500 ₽",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        Checkbox(
            title = stringResource(R.string.select_all),
            onCheck = {},
            isChecked = false,
            modifier = Modifier
                .padding(
                    start = MaterialTheme.paddings.medium,
                    bottom = MaterialTheme.paddings.medium
                    )
        )
    }

}

@Composable
fun HeadingAndPoints(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.checkout_loyality_system),
            color = colorScheme.secondary,
            style = MegahandTypography.titleLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Points()
    }

}

@Composable
fun Points(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        Checkbox(
            title = stringResource(R.string.withdraw_radio),
            onCheck = {},
            isChecked = false
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        Checkbox(
            title = stringResource(R.string.not_withdraw_radio),
            onCheck = {},
            isChecked = false
        )
    }
}