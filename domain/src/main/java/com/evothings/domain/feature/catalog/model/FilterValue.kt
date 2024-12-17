package com.evothings.domain.feature.catalog.model

import kotlinx.serialization.Serializable

@Serializable
data class FilterValue(
    val id: Int,
    val value: String
)