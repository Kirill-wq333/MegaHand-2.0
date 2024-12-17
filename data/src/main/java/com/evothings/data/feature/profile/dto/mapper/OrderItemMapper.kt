package com.evothings.data.feature.profile.dto.mapper

import com.evothings.data.feature.profile.dto.response.order.OrderItem
import com.evothings.domain.feature.profile.model.OrderHistoryProduct

internal fun Array<OrderItem>.toHistoryItems(): List<OrderHistoryProduct> {
    return this.map {
        OrderHistoryProduct(
            id = it.productId,
            photo = it.photo.orEmpty()
        )
    }
}