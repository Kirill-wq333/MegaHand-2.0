package com.evothings.mhand.presentation.feature.onboarding.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.profile.model.Referal
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.onboarding.model.Onboarding
import com.evothings.mhand.presentation.feature.onboarding.ui.components.Onboarding
import com.evothings.mhand.presentation.feature.onboarding.ui.components.OnboardingScrim
import com.evothings.mhand.presentation.feature.onboarding.ui.components.onboardingItem
import com.evothings.mhand.presentation.feature.onboarding.ui.components.onboardingItemOffset
import com.evothings.mhand.presentation.feature.profile.ui.state.data.Action
import com.evothings.mhand.presentation.feature.profile.ui.state.data.Referral
import com.evothings.mhand.presentation.feature.profile.ui.state.data.components.Block
import com.evothings.mhand.presentation.feature.profile.ui.state.data.components.BlockCashback
import com.evothings.mhand.presentation.feature.profile.ui.state.data.components.Data
import com.evothings.mhand.presentation.feature.profile.viewmodel.ProfileContract
import com.evothings.mhand.presentation.feature.shared.banner.RemovableBanner
import com.evothings.mhand.presentation.feature.shared.header.ui.Header
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.spacers
import kotlinx.coroutines.launch

@Composable
fun ProfileOnboarding(onFinish: () -> Unit) {

    var currentIndex by remember { mutableIntStateOf(0) }
    val currentItem = remember(currentIndex) {
        Onboarding.Profile.scenario[currentIndex]
    }

    val isLastElement = remember {
        derivedStateOf {
            currentIndex == Onboarding.Profile.scenario.lastIndex
        }
    }

    val elementsPositionMap = remember {
        mutableStateMapOf<Onboarding, Float>()
    }

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val scrollToNext = {
        scope.launch {
            if (!isLastElement.value) currentIndex++ else onFinish()
            val position = (elementsPositionMap[currentItem] ?: 0f).toInt()
            scrollState.animateScrollTo(position + 200)
        }
        Unit
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            Header(
                nameCategory = stringResource(R.string.profile_screen_title),
                balanceVisible = true,
                notificationVisible = true,
                onBack = {},
                onChooseCity = {}
            )
            RemovableBanner(
                bannerResource = R.drawable.profile_onboarding_banner,
                onClick = {},
                onHide = {}
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacers.extraLarge)
            ) {
                Action()
            }
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.large)
            )
            DemoPersonalInfo(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacers.medium)
                    .onboardingItem(
                        itemsMap = elementsPositionMap,
                        key = Onboarding.Profile.PersonalInfo,
                        hide = currentItem is Onboarding.Profile.PersonalInfo
                    )
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.large)
            )
            DemoCashbackCard(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacers.medium)
                    .onboardingItem(
                        itemsMap = elementsPositionMap,
                        key = Onboarding.Profile.CashbackBar,
                        hide = currentItem is Onboarding.Profile.CashbackBar
                    ),
                expandedState = false
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.large)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacers.extraLarge),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.large)
            ) {
                Block(
                    text = stringResource(R.string.shipping_address),
                    content = {}
                )
            }
            Block(
                text = "5GH78",
                visiblePrize = true,
                visibleText = true,
                content = {}
            )
            Spacer(
                modifier = Modifier.height(150.dp)
            )
        }

        OnboardingScrim(
            onClick = scrollToNext
        )

        when(currentItem) {

            is Onboarding.Profile.PersonalInfo -> {
                DemoPersonalInfo(
                    modifier = Modifier
                        .onboardingItemOffset(elementsPositionMap, currentItem)
                        .background(MaterialTheme.colorScheme.onSecondary)
                        .padding(horizontal = MaterialTheme.spacers.extraLarge)
                        .padding(bottom = MaterialTheme.spacers.medium)
                )
            }

            is Onboarding.Profile.CashbackBar -> {
                DemoCashbackCard(
                    modifier = Modifier
                        .onboardingItemOffset(elementsPositionMap, currentItem)
                        .background(MaterialTheme.colorScheme.onSecondary)
                        .padding(all = MaterialTheme.spacers.extraLarge),
                    expandedState = true
                )
            }

            is Onboarding.Profile.ReferalSystem -> {
                Block(
                    text = "5GH78",
                    visiblePrize = true,
                    visibleText = true,
                    content = {}
                )
            }
            else -> {}
        }

        Onboarding(
            icon = currentItem.iconRes,
            heading = currentItem.title,
            underHeading = currentItem.description,
            pageNumber = currentItem.itemsCounter,
            onFinish = isLastElement.value,
            alignment = currentItem.cardAlignment,
            visibleButtonTurnBack = (currentIndex != 0),
            onClickTurnBack = { currentIndex-- },
            onClickNext = scrollToNext
        )

    }

}

@Composable
private fun DemoPersonalInfo(modifier: Modifier) {
    Column(modifier) {
        Data(
            nameAndSurName = "Иванов Иван"
        )
    }
}

@Composable
private fun DemoCashbackCard(
    modifier: Modifier,
    expandedState: Boolean
) {

    val icon = remember(expandedState) {
        if (expandedState) R.drawable.ic_chevron_top else R.drawable.ic_chevron_bottom
    }

    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(0.05f),
                shape = MaterialTheme.shapes.large
            )
            .padding(MaterialTheme.spacers.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.cashback),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "5%",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(
                modifier = Modifier
                    .width(MaterialTheme.spacers.medium)
            )
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null
            )
        }
        if (expandedState) {
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.extraLarge)
            )
            BlockCashback(
                cashback = 5
            )
        }
    }

}

@Preview
@Composable
private fun ProfileOnboardingPreview() {
    MegahandTheme {
        Surface {
            ProfileOnboarding {}
        }
    }
}