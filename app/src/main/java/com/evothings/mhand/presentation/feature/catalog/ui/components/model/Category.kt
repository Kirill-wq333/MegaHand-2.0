package com.evothings.mhand.presentation.feature.catalog.ui.components.model

import com.evothings.mhand.R
import androidx.annotation.DrawableRes

sealed class Category(
    @DrawableRes val imageResId: Int,
    val title: String
) {

    data object Clothes : Category(
        imageResId = R.drawable.category_clothes,
        title = "Одежда"
    )

    data object Shoes : Category(
        imageResId = R.drawable.category_shoes,
        title = "Обувь"
    )

    data object Accessories : Category(
        imageResId = R.drawable.category_accessories,
        title = "Аксессуары"
    )

    data object Bags : Category(
        imageResId = R.drawable.category_bags,
        title = "Сумки"
    )

    data object Other : Category(
        imageResId = R.drawable.category_other,
        title = "Другое"
    )

    companion object {
        val entries = listOf(Clothes, Shoes, Accessories, Bags, Other)
    }

}