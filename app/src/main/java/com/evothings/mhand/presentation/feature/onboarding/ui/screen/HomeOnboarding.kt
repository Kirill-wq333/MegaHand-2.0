package com.evothings.mhand.presentation.feature.onboarding.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.onboarding.model.Onboarding
import com.evothings.mhand.presentation.feature.shared.product.callback.ProductCardCallback

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

}