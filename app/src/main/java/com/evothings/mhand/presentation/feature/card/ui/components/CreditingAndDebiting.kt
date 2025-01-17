package com.evothings.mhand.presentation.feature.card.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.evothings.domain.feature.card.model.TransactionType
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun CreditingAndDebiting(
    date: String,
    money: Double,
    type: TransactionType
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PLusAndMinusItem(
            money = money,
            type = type
        )
        Text(
            text = date,
            color = colorScheme.secondary.copy(.4f),
            style = MegahandTypography.bodyMedium
        )
    }

}




@Composable
fun PLusAndMinusItem(
    money: Double,
    type: TransactionType,
){
    val isDeposit = (type == TransactionType.DEPOSIT)
    val iconTint =
        if (isDeposit) colorScheme.inverseSurface else colorScheme.error

    val prefix = remember { if (isDeposit) "+" else "-" }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(color = iconTint.copy(.1f), shape = CircleShape)
        ){
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (isDeposit) R.drawable.ic_plus else R.drawable.ic_minus
                ),
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.large)
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
        Text(
            text =  "$prefix${money.splitHundreds()} ₽",
            color = colorScheme.secondary,
            style = MegahandTypography.headlineSmall
        )
    }

}