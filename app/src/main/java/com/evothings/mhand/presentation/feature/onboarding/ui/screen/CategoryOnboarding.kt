package com.evothings.mhand.presentation.feature.onboarding.ui.screen

//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateMapOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import com.evothings.mhand.R
//import com.evothings.mhand.presentation.feature.catalog.ui.components.SearchBar
//import com.evothings.mhand.presentation.feature.onboarding.model.CardAlignment
//import com.evothings.mhand.presentation.feature.onboarding.model.Onboarding
//import com.evothings.mhand.presentation.feature.onboarding.ui.components.Onboarding
//import com.evothings.mhand.presentation.feature.onboarding.ui.components.OnboardingScrim
//import com.evothings.mhand.presentation.feature.onboarding.ui.components.onboardingItem
//import com.evothings.mhand.presentation.theme.spacers
//
//@Composable
//fun CatalogOnboarding(
//    onFinish: () -> Unit
//) {
//
//    val itemsPositionMap = remember {
//        mutableStateMapOf<Onboarding, Float>()
//    }
//
//    val item = remember { Onboarding.Catalog.Categories }
//
//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(all = MaterialTheme.spacers.medium)
//        ) {
//            SearchBar(
//                modifier = Modifier.fillMaxWidth(),
//                query = "",
//                enableBackButton = false,
//                onChangeQuery = {},
//                onSearch = {},
//                onBack = {}
//            )
//            Spacer(
//                modifier = Modifier
//                    .height(MaterialTheme.spacers.extraLarge)
//            )
//            OnboardingCategoriesGrid(
//                modifier = Modifier
//                    .onboardingItem(
//                        itemsMap = itemsPositionMap,
//                        key = Onboarding.Catalog.Categories,
//                        hide = true
//                    )
//            )
//        }
//
//        OnboardingScrim(
//            onClick = onFinish
//        )
//
//
//        Onboarding(
//            heading = item.title,
//            underHeading = item.itemsCounter,
//            pageNumber = item.description,
//            icon = item.iconRes,
//            onFinish = true,
//            visibleButtonTurnBack = false
//        )
//
//    }
//
//}