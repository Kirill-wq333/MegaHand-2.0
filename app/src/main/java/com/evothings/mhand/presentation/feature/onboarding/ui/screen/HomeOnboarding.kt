package com.evothings.mhand.presentation.feature.onboarding.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.domain.util.Mock
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.home.ui.LoyalityCard
import com.evothings.mhand.presentation.feature.home.ui.NewProduct
import com.evothings.mhand.presentation.feature.home.ui.components.StoriesItem
import com.evothings.mhand.presentation.feature.onboarding.model.CardAlignment
import com.evothings.mhand.presentation.feature.onboarding.ui.components.Onboarding
import com.evothings.mhand.presentation.feature.onboarding.ui.components.onboardingItem
import com.evothings.mhand.presentation.feature.onboarding.ui.components.onboardingItemOffset
import com.evothings.mhand.presentation.feature.shared.MLazyRow
import com.evothings.mhand.presentation.feature.onboarding.model.Onboarding
import com.evothings.mhand.presentation.feature.onboarding.ui.components.OnboardingScrim
import com.evothings.mhand.presentation.feature.shared.header.ui.Header
import com.evothings.mhand.presentation.feature.shared.product.callback.ProductCardCallback
import com.evothings.mhand.presentation.theme.MegahandTheme

@Composable
fun HomeOnboarding(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit
) {
    val storiesList = remember {
        listOf(
            Pair(
                "О магазинах Волгограда", R.drawable.onboarding_story_1
            ),
            Pair(
                "Новые поступления", R.drawable.onboarding_story_2
            ),
            Pair(
                "Одежда бренда Crisitian Dior", R.drawable.onboarding_story_3
            ),
            Pair(
                "Купоны и скидки в приложении", R.drawable.onboarding_story_4
            ),
        )
    }

    val emptyProductCardCallback = object : ProductCardCallback {
        override fun addToCart(id: Int) {}
        override fun openProductDetailScreen(id: Int) {}
        override fun toggleFavourite(id: Int) {}
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    val currentItem = remember(currentIndex) {
        Onboarding.Home.scenario[currentIndex]
    }

    val isLastElement = remember {
        derivedStateOf {
            currentIndex == Onboarding.Home.scenario.lastIndex
        }
    }


    val elementsPositionMap = remember {
        mutableStateMapOf<Onboarding, Float>()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Header(
                modifier = Modifier
                    .onboardingItem(
                        itemsMap = elementsPositionMap,
                        key = Onboarding.Home.Header,
                        hide = currentItem is Onboarding.Home.Header
                    ),
                nameCategory = "",
                logoVisible = true,
                locationVisible = true,
                balanceVisible = true,
                notificationVisible = true,
                chevronLeftVisible = false
            )
            MLazyRow(
                modifier = Modifier
                    .onboardingItem(
                        itemsMap = elementsPositionMap,
                        key = Onboarding.Home.Stories,
                        hide = currentItem is Onboarding.Home.Stories
                    ),
                itemsList = storiesList,
                itemContent = { story, _ ->
                    StoriesItem(
                        storiesImage = story.second,
                        textStories = story.first
                    )
                }
            )

            LoyalityCard(
                modifier = Modifier
                    .onboardingItem(
                        itemsMap = elementsPositionMap,
                        key = Onboarding.Home.Loyality,
                        hide = currentItem is Onboarding.Home.Loyality
                    ),
                cashback = 3,
                openProfile = {},
                enableBalance = true
            )

            NewProduct(
                modifier = Modifier
                    .onboardingItem(
                        itemsMap = elementsPositionMap,
                        key = Onboarding.Home.NewProducts,
                        hide = currentItem is Onboarding.Home.NewProducts
                    ),
                products = Mock.demoProductsList,
                callback = emptyProductCardCallback
            )

        }

        OnboardingScrim (
            onClick = {
                if (!isLastElement.value) currentIndex++ else onFinish()
            }
        )

        when (currentItem) {
            Onboarding.Home.Header ->
                Header(
                    modifier = Modifier,
                    logoVisible = true,
                    balanceVisible = true,
                    locationVisible = true,
                    notificationVisible = true,
                    chevronLeftVisible = false,
                    nameCategory = ""
                )

            Onboarding.Home.Stories -> {
                Box(
                    modifier = Modifier
                ) {
                    MLazyRow(
                        modifier = Modifier
                            .offset(
                                y = (elementsPositionMap[currentItem] ?: 0f).dp - 12.dp
                            )
                            .background(MaterialTheme.colorScheme.background)
                            .padding(vertical = 12.dp),
                        itemsList = storiesList,
                        itemContent = { story, _ ->
                            StoriesItem(
                                storiesImage = story.second,
                                textStories = story.first,
                            )
                        }
                    )
                }
            }

            Onboarding.Home.Loyality ->
                LoyalityCard(
                    modifier = Modifier
                        .onboardingItemOffset(elementsPositionMap, currentItem)
                        .background(
                            color = MaterialTheme.colorScheme.onSecondary,
                        ),
                    cashback = 3,
                    openProfile = {},
                    enableBalance = true
                )

            Onboarding.Home.NewProducts -> {
                NewProduct(
                    modifier = Modifier
                        .onboardingItemOffset(elementsPositionMap, currentItem)
                        .background(
                            color = MaterialTheme.colorScheme.onSecondary
                        ),
                    products = Mock.demoProductsList,
                    callback = emptyProductCardCallback
                )
            }

            else -> {}
        }

        Onboarding(
            heading = currentItem.title,
            icon = currentItem.iconRes,
            underHeading = currentItem.itemsCounter,
            visibleButtonTurnBack = (currentIndex != 0),
            onFinish = isLastElement.value,
            pageNumber = currentItem.description,
            alignment = currentItem.cardAlignment
        )
    }
}

@Preview
@Composable
private fun HomeOnboardingPreview() {
    MegahandTheme(false) {
        Surface {
            HomeOnboarding(
                onFinish = {}
            )
        }
    }
}