package com.evothings.mhand.presentation.feature.card.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun CreditingAndDebiting(
    day: String,
    icon: ImageVector,
    date: String,
    color: Color,
    colorIcon: Color,
    money: String,
    selected: Boolean
){

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = day,
            color = colorScheme.secondary.copy(.4f),
            style = MegahandTypography.labelLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PLusAndMinusItem(
                icon = icon,
                color = color,
                colorIcon = colorIcon,
                money = money,
                selected = selected
            )
            Text(
                text = date,
                color = colorScheme.secondary.copy(.4f),
                style = MegahandTypography.bodyMedium
            )
        }
    }

}


@Composable
fun PLusAndMinusItem(
    icon: ImageVector,
    color: Color,
    colorIcon: Color,
    money: String,
    selected: Boolean
){

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(color = color, shape = CircleShape)
        ){
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colorIcon,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.large)
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
        Text(
            text =  if (selected)"$money ₽" else "-$money ₽",
            color = colorScheme.secondary,
            style = MegahandTypography.headlineSmall
        )
    }

}