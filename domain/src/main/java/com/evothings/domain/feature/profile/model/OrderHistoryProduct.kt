package com.evothings.domain.feature.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderHistoryProduct(
    val id: Int,
    val photo: String
)
