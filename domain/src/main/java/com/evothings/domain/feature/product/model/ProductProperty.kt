package com.evothings.domain.feature.product.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductProperty(
    val name: String,
    val value: String
)
