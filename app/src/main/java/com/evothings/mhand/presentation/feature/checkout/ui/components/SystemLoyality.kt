package com.evothings.mhand.presentation.feature.checkout.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.checkbox.Checkbox
import com.evothings.mhand.presentation.feature.shared.loyalityCard.Balance
import com.evothings.mhand.presentation.feature.shared.loyalityCard.Cashback
import com.evothings.mhand.presentation.feature.shared.radio.RadioButton
import com.evothings.mhand.presentation.feature.shared.text.LabelTextField
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.feature.shared.text.transform.rememberRubleVisualTransformation
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers


@Composable
fun SystemLoyality(
    modifier: Modifier = Modifier,
    balance: Int,
    cashback: Int,
    amount: String,
    availableBalance: Double,
    onChangeAmount: (String) -> Unit,
    onCheckWithdraw: (Boolean) -> Unit,
    isWithdraw: Boolean
) {
    Column(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.paddings.extraGiant)
    ) {
        HeadingAndPoints(
            isWithdraw = isWithdraw,
            onCheckWithdraw = onCheckWithdraw
        )
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
                    balance = balance,
                    isOffline = false,
                    modifier = Modifier
                        .size(24.dp)
                )
                Cashback(
                    cashback = cashback,
                    isOffline = false,
                    isSmallSize = false
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
        if (isWithdraw) {
            AmountAndSelectAll(
                balance = balance,
                availableBalance = availableBalance,
                amount = amount,
                onChangeAmount = onChangeAmount
            )
        }
    }
}

@Composable
fun AmountAndSelectAll(
    modifier: Modifier = Modifier,
    balance: Int,
    availableBalance: Double,
    amount: String,
    onChangeAmount: (String) -> Unit
) {

    val withdrawAllCheckboxState = remember(amount) {
        amount.toIntOrNull() == balance
    }

    val isWrongAmount = remember(amount) {
        if (amount.isNotEmpty()) {
            amount.toDouble() > availableBalance
        } else {
            false
        }
    }

    Column {
        Text(
            text = stringResource(R.string.amount),
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        LabelTextField(
            value = amount,
            label = stringResource(R.string.amount),
            isError = isWrongAmount,
            errorText = stringResource(R.string.incorrect_withdraw_amount_error_text),
            visualTransformation = rememberRubleVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            onValueChange = { changed ->
                val trimmed = changed.trimStart('0')
                if (trimmed.isEmpty() || trimmed.toInt() <= balance) {
                    onChangeAmount(trimmed)
                }
            }
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        Checkbox(
            title = stringResource(R.string.withdraw_all),
            isChecked = withdrawAllCheckboxState,
            onCheck = {
                if (withdrawAllCheckboxState) {
                    onChangeAmount("0")
                } else {
                    onChangeAmount(balance.toString())
                }
            }
        )
    }

}

@Composable
fun HeadingAndPoints(
    modifier: Modifier = Modifier,
    isWithdraw: Boolean,
    onCheckWithdraw: (Boolean) -> Unit
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
        Points(
            isWithdraw = isWithdraw,
            onCheckWithdraw = onCheckWithdraw
        )
    }

}

@Composable
fun Points(
    modifier: Modifier = Modifier,
    isWithdraw: Boolean,
    onCheckWithdraw: (Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        RadioButton(
            title = stringResource(R.string.withdraw_radio),
            onSelect = { onCheckWithdraw(true) },
            isSelected = isWithdraw
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        RadioButton(
            title = stringResource(R.string.not_withdraw_radio),
            onSelect = { onCheckWithdraw(false) },
            isSelected = !isWithdraw
        )
    }
}