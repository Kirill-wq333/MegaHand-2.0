package com.evothings.data.feature.checkout.dto.mapper

import com.evothings.data.feature.checkout.dto.response.DeliveryPriceResponse
import com.evothings.domain.feature.checkout.model.DeliveryPrice

internal fun DeliveryPriceResponse.toDeliveryPrice(): DeliveryPrice {
    return DeliveryPrice(
        deliveryPrice = this.deliveryPrice.toDouble(),
        orderTotalCost = this.totalPrice.toDouble()
    )
}