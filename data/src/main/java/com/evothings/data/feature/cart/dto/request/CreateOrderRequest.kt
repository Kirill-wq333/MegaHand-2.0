package com.evothings.data.feature.cart.dto.request

import com.evothings.data.feature.product.dto.request.ProductId

data class CreateOrderRequest(
    val products: Array<ProductId>
)
