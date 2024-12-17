package com.evothings.mhand.presentation.feature.shared.product.callback

import androidx.compose.runtime.Stable

@Stable
object EmptyProductCardCallback : ProductCardCallback {
    override fun addToCart(id: Int) {}

    override fun openProductDetailScreen(id: Int) {}

    override fun toggleFavourite(id: Int) {}
}