package com.evothings.domain.feature.home.model

import kotlinx.serialization.Serializable

@Serializable
data class Brand(
    val id: Int,
    val photoLink: String,
    val name: String
)
