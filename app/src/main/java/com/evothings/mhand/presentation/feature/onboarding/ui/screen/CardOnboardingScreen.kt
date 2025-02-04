package com.evothings.mhand.presentation.feature.onboarding.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.domain.util.Mock
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.card.ui.components.CreditingAndDebiting
import com.evothings.mhand.presentation.feature.card.ui.components.HistoryBar
import com.evothings.mhand.presentation.feature.card.viewmodel.enumeration.CardFilterType
import com.evothings.mhand.presentation.feature.home.ui.LoyalityCard
import com.evothings.mhand.presentation.feature.onboarding.model.Onboarding
import com.evothings.mhand.presentation.feature.onboarding.ui.components.OnboardingScrim
import com.evothings.mhand.presentation.feature.onboarding.ui.components.Onboardings
import com.evothings.mhand.presentation.feature.onboarding.ui.components.onboardingItem
import com.evothings.mhand.presentation.feature.shared.banner.RemovableBanner
import com.evothings.mhand.presentation.feature.shared.header.ui.Header
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun CardOnboardingScreen(onFinish: () -> Unit) {

    var currentIndex by remember { mutableIntStateOf(0) }
    val currentItem = remember(currentIndex) {
        Onboarding.Card.scenario[currentIndex]
    }

    val itemsPositionMap = remember {
        mutableStateMapOf<Onboarding, Float>()
    }

    val isLastElement = remember {
        derivedStateOf {
            currentIndex == Onboarding.Card.scenario.lastIndex
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Header(
                nameCategory = stringResource(id = R.string.card_screen_title),
                locationVisible = false,
                balanceVisible = false,
                onBack = {},
                onChooseCity = {},
                turnButtonVisible = false,
                notificationVisible = true,
                logoVisible = false
            )
            DemoLoyalityCard(
                modifier = Modifier
                    .onboardingItem(
                        itemsMap = itemsPositionMap,
                        key = Onboarding.Card.CardInformation,
                        hide = currentItem is Onboarding.Card.CardInformation
                    )
            )
            RemovableBanner(
                bannerResource = R.drawable.loyality_onboarding_banner,
                onClick = {},
                onHide = {}
            )
            TransactionsHistory(
                modifier = Modifier
                    .onboardingItem(
                        itemsMap = itemsPositionMap,
                        key = Onboarding.Card.Transactions,
                        hide = currentItem is Onboarding.Card.Transactions
                    )
            )
        }

        OnboardingScrim(
            onClick = { if (!isLastElement.value) currentIndex++ else onFinish() }
        )

        when(currentItem) {

            is Onboarding.Card.CardInformation -> {
                DemoLoyalityCard(
                    modifier = Modifier
                        .offset(
                            y = (itemsPositionMap[Onboarding.Card.CardInformation] ?: 0f).dp
                        )
                        .background(MaterialTheme.colorScheme.onSecondary)

                )
            }

            is Onboarding.Card.Transactions -> {
                TransactionsHistory(
                    modifier = Modifier
                        .offset(
                            y = (itemsPositionMap[Onboarding.Card.Transactions] ?: 0f).dp
                        )
                        .background(MaterialTheme.colorScheme.onSecondary)
                )
            }

            else -> {}
        }

        Onboardings(
            heading = currentItem.title,
            underHeading = currentItem.description,
            icon = currentItem.iconRes,
            pageNumber = currentItem.itemsCounter,
            onFinish = isLastElement.value,
            visibleButtonTurnBack = true,
            alignment = currentItem.cardAlignment,
            onClickTurnBack = { currentIndex-- },
            onClickNext = { if (!isLastElement.value) currentIndex++ else onFinish() }
        )

    }
}

@Composable
private fun DemoLoyalityCard(modifier: Modifier) {
    LoyalityCard(
        modifier = modifier.padding(MaterialTheme.spacers.medium),
        cashback = 15,
        openProfile = {},
        enableBalance = true,
        cardQRUrl = "",
        showQR = {}
    )
}

@Composable
private fun TransactionsHistory(
    modifier: Modifier
) {

    val transactions = remember { Mock.demoTransactionsMap }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(
                vertical = MaterialTheme.spacers.large,
                horizontal = MaterialTheme.spacers.extraLarge
            ),
    ) {
        HistoryBar(
            isFilterPickerExpanded = false,
            currentFilter = CardFilterType.YEAR,
            onExpand = {}
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Mock.demoTransactionsMap.entries.forEach { entry ->
            val items = remember { transactions[entry.key] } ?: return

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    space = MaterialTheme.spacers.medium
                )
            ) {
                Text(
                    text = entry.key,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary.copy(0.4f)
                )
                repeat(items.size) { i ->
                    val item = remember { items[i] }
                    CreditingAndDebiting(
                        date = item.date,
                        money = item.amount,
                        type = item.type
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(MaterialTheme.spacers.large)
                )
            }
        }
    }
}

@Preview
@Composable
private fun CardOnboardingPreview() {
    MegahandTheme {
        Surface {
            CardOnboardingScreen(onFinish = {})
        }
    }
}