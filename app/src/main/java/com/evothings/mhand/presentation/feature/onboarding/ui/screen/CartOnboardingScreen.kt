package com.evothings.mhand.presentation.feature.onboarding.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.util.Mock
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.cart.ui.components.CartSelectionOptions
import com.evothings.mhand.presentation.feature.cart.ui.components.productComponent.Cart
import com.evothings.mhand.presentation.feature.onboarding.model.CardAlignment
import com.evothings.mhand.presentation.feature.onboarding.model.Onboarding
import com.evothings.mhand.presentation.feature.onboarding.ui.components.OnboardingScrim
import com.evothings.mhand.presentation.feature.onboarding.ui.components.Onboardings
import com.evothings.mhand.presentation.feature.onboarding.ui.components.onboardingItem
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun CartOnboardingScreen(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit
) {
    val itemPositionMap = remember {
        mutableStateMapOf<Onboarding, Float>()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column {
            CartHeading()
            CartSelectionOptions(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacers.medium),
                selectAllChecked = false,
                onCheckSelectAll = {},
                onClear = {}
            )
            DemoProduct(
                modifier = Modifier
                    .padding(MaterialTheme.spacers.medium)
                    .onboardingItem(
                        itemsMap = itemPositionMap,
                        key = Onboarding.Cart.CartItem,
                        hide = true
                    ),
                product = Mock.demoProduct
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
            DemoProduct(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacers.medium),
                product = Mock.demoProductsList[1]
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
            DemoProduct(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacers.medium),
                product = Mock.demoProductsList[2]
            )
        }

        OnboardingScrim(
            onClick = onFinish
        )

        DemoProduct(
            modifier = Modifier
                .offset(
                    y = (itemPositionMap[Onboarding.Cart.CartItem] ?: 0f).dp
                )
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(MaterialTheme.spacers.medium),
            product = Mock.demoProduct
        )

        with(Onboarding.Cart.CartItem) {
            Onboardings(
                icon = iconRes,
                heading = title,
                underHeading = description,
                pageNumber = itemsCounter,
                onFinish = true,
                alignment = CardAlignment.BOTTOM,
                onClickNext = onFinish,
                onClickTurnBack = {}
            )
        }

    }
}

@Composable
fun CartHeading() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = stringResource(R.string.cart),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(
                    all = MaterialTheme.spacers.large
                )
        )
    }
}

@Composable
private fun DemoProduct(
    modifier: Modifier,
    product: Product
) {
    Cart(
        modifier = modifier,
        model = product,
        isSelected = true,
        onSelect = {},
        onClick = {},
        toggleFavourite = {},
        removeFromCart = {}
    )
}

@Preview
@Composable
private fun CartOnboardingPreview() {
    MegahandTheme {
        Surface {
            CartOnboardingScreen(
                onFinish = {}
            )
        }
    }
}