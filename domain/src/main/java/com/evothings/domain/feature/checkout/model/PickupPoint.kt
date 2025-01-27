package com.evothings.domain.feature.checkout.model

data class PickupPoint(
    val code: String = "",
    val address: String = "",
    val point: MapPoint = MapPoint(0.0, 0.0)
)
