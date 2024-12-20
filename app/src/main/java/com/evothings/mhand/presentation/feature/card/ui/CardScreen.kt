package com.evothings.mhand.presentation.feature.card.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.card.ui.components.CreditingAndDebiting
import com.evothings.mhand.presentation.feature.card.ui.components.HistoryBar
import com.evothings.mhand.presentation.feature.home.ui.LoyalityCard
import com.evothings.mhand.presentation.feature.home.ui.components.CouponBanner
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.BottomBarNavigation
import com.evothings.mhand.presentation.feature.shared.header.Header
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun CardScreen(){
    Scaffold(
        topBar = {

            Header(
                nameCategory = "Моя Карта",
                money = "0",
                chevronLeftVisible = false,
                logoVisible = false,
                balanceVisible = false,
                locationVisible = false,
                notificationVisible = true
            )

        },
        bottomBar = { BottomBarNavigation() }
    ) {

        Box(modifier = Modifier.padding(it)) {
            Content()
        }

    }
}

@Composable
private fun Content() {

    val creditingAndDebitingList = listOf(
        CreditingAndDebitingClass(
            day = "Сегодня",
            date = "16:30",
            money = "200",
            color = colorScheme.inverseSurface.copy(.1f),
            colorIcon = colorScheme.inverseSurface,
            icon = ImageVector.vectorResource(R.drawable.ic_plus),
            selected = true
        ),
        CreditingAndDebitingClass(
            day = "25 ноября",
            date = "01:25",
            money = "500",
            color = colorScheme.error.copy(.1f),
            colorIcon = colorScheme.error,
            icon = ImageVector.vectorResource(R.drawable.ic_minus),
            selected = false
        )

    )


    Column(
        modifier = Modifier
            .padding(MaterialTheme.paddings.extraLarge)
    ) {
        LoyalityCard(
            money = "0",
            visibleCashback = false,
            visible = true
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.mega))
        CouponBanner(
            banner = R.drawable.loyality_onboarding_banner,
            selected = true
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        HistoryBar()
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraLarge)
        ) {
            items(creditingAndDebitingList){ item ->
                CreditingAndDebiting(
                    day = item.day,
                    date = item.date,
                    money = item.money,
                    selected = item.selected,
                    color = item.color,
                    colorIcon = item.colorIcon,
                    icon = item.icon
                )
            }
        }
    }

}

data class CreditingAndDebitingClass(
    val day: String,
    val date: String,
    val money: String,
    val color: Color,
    val colorIcon: Color,
    val icon: ImageVector,
    val selected: Boolean
)

@Preview
@Composable
fun PreviewCardScreen(){
    MegahandTheme {
        CardScreen()
    }
}