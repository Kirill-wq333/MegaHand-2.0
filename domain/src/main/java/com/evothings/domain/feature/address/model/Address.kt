package com.evothings.domain.feature.address.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val id: Int = 0,
    val fullAddress: String = "",
    val city: String = "",
    val street: String = "",
    val house: String = "",
    val flat: String = "",
    val postalCode: String = "",
    val main: Boolean = false,
)
