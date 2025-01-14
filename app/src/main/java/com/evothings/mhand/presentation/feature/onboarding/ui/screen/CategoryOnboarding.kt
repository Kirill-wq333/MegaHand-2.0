package com.evothings.mhand.presentation.feature.onboarding.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.Category.components.categories.CategoryItem
import com.evothings.mhand.presentation.feature.Category.components.categories.CategoryItemOnboarding
import com.evothings.mhand.presentation.feature.catalog.ui.components.SearchBar
import com.evothings.mhand.presentation.feature.onboarding.model.CardAlignment
import com.evothings.mhand.presentation.feature.onboarding.model.Onboarding
import com.evothings.mhand.presentation.feature.onboarding.ui.components.Onboarding
import com.evothings.mhand.presentation.feature.onboarding.ui.components.OnboardingScrim
import com.evothings.mhand.presentation.feature.onboarding.ui.components.onboardingItem
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun CatalogOnboarding(
    onFinish: () -> Unit
) {

    val itemsPositionMap = remember {
        mutableStateMapOf<Onboarding, Float>()
    }

    val item = remember { Onboarding.Catalog.Categories }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MaterialTheme.spacers.medium)
        ) {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = "",
                enableBackButton = false,
                onChangeQuery = {},
                onSearch = {},
                onBack = {}
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.extraLarge)
            )
            OnboardingCategoriesGrid(
                modifier = Modifier
                    .onboardingItem(
                        itemsMap = itemsPositionMap,
                        key = Onboarding.Catalog.Categories,
                        hide = true
                    )
            )
        }

        OnboardingScrim(
            onClick = onFinish
        )

        OnboardingCategoriesGrid(
            modifier = Modifier
                .offset(
                    y = (itemsPositionMap[Onboarding.Catalog.Categories] ?: 0f).dp
                )
                .background(Color.White)
                .padding(MaterialTheme.spacers.medium),
        )

        Onboarding(
            heading = item.title,
            underHeading = item.description,
            pageNumber = item.itemsCounter,
            icon = item.iconRes,
            onFinish = true,
            visibleButtonTurnBack = false,
            alignment = CardAlignment.BOTTOM
        )

    }

}


@Composable
private fun OnboardingCategoriesGrid(modifier: Modifier) {

    val categories = remember {
        mapOf<String, Int>(
            "Одежда" to R.drawable.category_clothes,
            "Обувь" to R.drawable.category_shoes,
            "Аксессуары" to R.drawable.category_accessories,
            "Сумки" to R.drawable.category_bags,
            "Другое" to R.drawable.category_other
        )
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.paddings.small),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.paddings.small),
    ) {

        items(categories.entries.toList()) { (title, imageRes) ->
            CategoryItemOnboarding(
                image = imageRes,
                title = title,
                onClick = {}
            )
        }

    }
}

@Preview
@Composable
private fun PreviewOnboardingCatalog() {
    MegahandTheme {
        CatalogOnboarding(
            onFinish = {}
        )
    }
}