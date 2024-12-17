package com.evothings.mhand.presentation.feature.shared.product.callback

interface ProductCardCallback {
    fun openProductDetailScreen(id: Int)
    fun addToCart(id: Int)
    fun toggleFavourite(id: Int)
}