package com.evothings.data.feature.cart.dto.mapper

import com.evothings.data.feature.cart.dto.response.CartResponse
import com.evothings.data.feature.product.dto.mapper.toProduct
import com.evothings.domain.feature.cart.model.Cart

internal fun CartResponse.toCart(): Cart {
    return Cart(
        products = products.map { it.product.toProduct() },
        total = basePrice.toDouble(),
        finalPrice = summary.toDouble(),
        discount = discount.toDouble(),
        cashbackPoints = cashbackPoints.toDouble()
    )
}

internal fun CartResponse.toCalculatedAmountCart(): Cart {
    return Cart(
        products = listOf(),
        total = basePrice.toDouble(),
        finalPrice = summary.toDouble(),
        discount = discount.toDouble(),
        cashbackPoints = cashbackPoints.toDouble()
    )
}