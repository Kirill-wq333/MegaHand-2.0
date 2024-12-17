package com.evothings.data.feature.cart.dto.mapper

import com.evothings.data.feature.cart.dto.request.CreateOrderRequest
import com.evothings.data.feature.product.dto.mapper.toProductIdRequest

internal fun List<Int>.toCreateOrderRequest(): CreateOrderRequest {
    return CreateOrderRequest(
        products = this.map { it.toProductIdRequest() }.toTypedArray()
    )
}