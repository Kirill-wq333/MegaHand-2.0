package com.evothings.domain.feature.checkout.model

import kotlinx.serialization.Serializable

@Serializable
data class MapPoint(
    val latitude: Double,
    val longitude: Double
)
