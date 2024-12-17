package com.evothings.domain.feature.card.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val type: TransactionType,
    val amount: Double,
    val date: String
)