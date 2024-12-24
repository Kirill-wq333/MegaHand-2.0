package com.evothings.mhand.presentation.feature.shared.loyalityCard


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.SmallButton
import com.evothings.mhand.presentation.feature.shared.text.util.NumberSeparator
import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes
import com.evothings.mhand.presentation.theme.values.paddings.LocalPaddings


@Composable
fun BalanceAndCashback(
    money: Int = -1,
    cashback: Int,
    enableBalance: Boolean,
    isOffline: Boolean = false,
    burnAmount: Double = -1.0,
    burnDate: String = "",
    onClickIncrease: () -> Unit
) {
    val maximumCashbackIsReached = remember { cashback == 5 }
    val showIncreaseButton = remember(isOffline) { !maximumCashbackIsReached && !isOffline }

    Box(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .border(
                width = 1.dp,
                color = colorScheme.primary.copy(.05f),
                shape = RoundedCornerShape(topStart = 9.dp, bottomStart = 9.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .padding(MaterialTheme.paddings.giant)
        ) {
            if (enableBalance) {
                Balance(
                    isOffline = isOffline,
                    balance = money,
                )
            }
            if (burnAmount > 0) {
                var fontSize by remember { mutableStateOf(12.sp) }

                Text(
                    text = stringResource(R.string.card_burn_bonuses_text, burnAmount, burnDate),
                    style = typography.bodyMedium,
                    fontSize = fontSize,
                    onTextLayout = { if (it.hasVisualOverflow) fontSize = 10.sp },
                    maxLines = 1,
                    color = colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Cashback(
                isOffline = isOffline,
                isSmallSize = enableBalance,
                cashback = cashback
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
            TextCashBack(
                cashback = cashback
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
            if (showIncreaseButton) {
                SmallButton(
                    text = stringResource(id = R.string.increase),
                    backgroundColor = colorScheme.secondary.copy(0.05f),
                    onClick = onClickIncrease
                )
            }
        }

    }
}

@Composable
fun Balance(
    isOffline: Boolean,
    balance: Int
){

    val balanceFormatted = remember {
        if (!isOffline) {
            val balanceSplit = balance.splitHundreds(NumberSeparator.SPACE)
            "$balanceSplit ₽"
        } else "??? ₽"
    }

    val tint =
        if (isOffline) colorScheme.secondary.copy(0.4f) else colorScheme.inverseSurface

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_back),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        Text(
            text = balanceFormatted,
            color = colorScheme.secondary,
            style = MegahandTypography.bodyLarge
        )
    }
}
@Composable
fun Cashback(
    cashback: Int,
    isOffline: Boolean,
    isSmallSize: Boolean
){
    val turnIconTint =
        if (!isOffline) colorScheme.inverseSurface else colorScheme.secondary.copy(0.4f)

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_prize),
            contentDescription = null,
            tint = turnIconTint
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        Text(
            text = if (!isOffline) "$cashback%" else "?%",
            color = colorScheme.secondary,
            style = if (isSmallSize) typography.bodyMedium else typography.headlineMedium
        )
    }
}

@Composable
fun TextCashBack(
    cashback: Int,
    isOffline: Boolean = false
){
    val maximumCashbackIsReached = remember { cashback == 5 }

    if (!isOffline) {
        Text(
            text = stringResource(
                id = if (!maximumCashbackIsReached)
                    R.string.loyality_card_hint
                else
                    R.string.maximum_cashback_reached
            ),
            style = typography.bodyMedium,
            color = colorScheme.secondary.copy(0.4f)
        )
    }
}

