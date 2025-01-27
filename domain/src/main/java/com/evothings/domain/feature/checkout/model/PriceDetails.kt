package com.evothings.domain.feature.checkout.model

data class PriceDetails(
    val basePrice: Double,
    val cashbackPoints: Double,
    val pointsDiscount: Double,
    val discount: Double,
    val price: Double,
)
