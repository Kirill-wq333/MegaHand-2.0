package com.evothings.domain.feature.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class ReferalInfo(
    val balance: Int = 0,
    val referalsList: List<Referal> = emptyList()
)

@Serializable
data class Referal(
    val name: String,
    val cashback: Int,
    val joinDate: String
)