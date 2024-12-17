package com.evothings.domain.feature.catalog.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductCategory(
    val id: Int = -1,
    val title: String = "",
    val photoLink: String? = null,
    val parent: ProductCategory? = null,
    val children: List<ProductCategory>? = null
)
