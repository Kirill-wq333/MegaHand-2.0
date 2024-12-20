package com.evothings.mhand.presentation.feature.shared.header

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.header.components.BackButton
import com.evothings.mhand.presentation.feature.shared.header.components.Logo
import com.evothings.mhand.presentation.feature.shared.header.components.PrizeAndMoney
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun Header(
    modifier: Modifier = Modifier,
    nameCategory: String,
    money: String = "0",
    logo: ImageVector = ImageVector.vectorResource(R.drawable.logo),
    location: ImageVector = ImageVector.vectorResource(R.drawable.ic_location),
    notification: ImageVector = ImageVector.vectorResource(R.drawable.ic_notifications),
    prize: ImageVector = ImageVector.vectorResource(R.drawable.ic_prize),
    back: ImageVector = ImageVector.vectorResource(R.drawable.ic_chevron_left),
    logoVisible: Boolean,
    balanceVisible: Boolean,
    notificationVisible: Boolean,
    locationVisible: Boolean,
    chevronLeftVisible: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.extraLarge),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Logo(
                logo = logo,
                visible = logoVisible
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (chevronLeftVisible) {
                    BackButton(
                        icon = back
                    )
                    Spacer(modifier = modifier.width(MaterialTheme.spacers.medium))
                }
                Text(
                    text = nameCategory,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
                    fontWeight = FontWeight.W500
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                PrizeAndMoney(
                    prize = prize,
                    money = money,
                    selected = balanceVisible
                )
                Spacer(modifier = modifier.width(MaterialTheme.spacers.normal))
                IconNavigation(
                    imageVector = location,
                    contentDescription = "location",
                    visible = locationVisible
                )
                IconNavigation(
                    imageVector = notification,
                    contentDescription = "notification",
                    visible = notificationVisible
                )
            }
        }

    }
}


@Composable
fun IconNavigation(
    imageVector: ImageVector,
    contentDescription: String?,
    visible: Boolean
) {
    if (visible) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.large)
            )
        }
    }

}


@Preview
@Composable
fun PreviewHeader(){
    Header(
        nameCategory = "Магазины",
        money = "7 180",
        back = ImageVector.vectorResource(R.drawable.ic_chevron_left),
        notification = ImageVector.vectorResource(R.drawable.ic_notifications),
        location = ImageVector.vectorResource(R.drawable.ic_location),
        logo = ImageVector.vectorResource(R.drawable.logo),
        prize = ImageVector.vectorResource(R.drawable.ic_prize),
        chevronLeftVisible = true,
        logoVisible = false,
        notificationVisible = true,
        locationVisible = true,
        balanceVisible = true
    )
}
