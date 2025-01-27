package com.evothings.data.feature.checkout.dto.mapper

import com.evothings.data.feature.checkout.dto.response.PriceDetailsResponse
import com.evothings.domain.feature.checkout.model.PriceDetails

internal fun PriceDetailsResponse.toPriceDetails(): PriceDetails {
    return PriceDetails(
        basePrice = basePrice.toDouble(),
        cashbackPoints = pointsCashback.toDouble(),
        pointsDiscount = pointsDiscount?.toDouble() ?: 0.0,
        discount = discount.toDouble(),
        price = totalPrice.toDouble()
    )
}
