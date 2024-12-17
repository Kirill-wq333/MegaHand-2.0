package com.evothings.domain.feature.card.model

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val balance: Int = 7180,
    val barcodeUrl: String? = "",
    val burnAmount: Double = 0.0,
    val burnDate: String? = "",
    val cashbackLevel: Int = 0
)
