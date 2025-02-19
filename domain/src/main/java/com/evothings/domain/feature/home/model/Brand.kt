package com.evothings.domain.feature.home.model

import kotlinx.serialization.Serializable

@Serializable
data class Brand(
    val id: Int = -1,
    val photoLink: String = "",
    val name: String = ""
)
