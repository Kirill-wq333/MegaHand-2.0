package com.evothings.domain.feature.cart.model

import com.evothings.domain.feature.product.model.Product

data class Cart(
    val products: List<Product>,
    val total: Double,
    val discount: Double,
    val finalPrice: Double,
    val cashbackPoints: Double
)
