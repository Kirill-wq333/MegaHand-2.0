package com.evothings.domain.feature.catalog.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface SearchHint

@Serializable
data class TextHint(
    val selectionRange: List<Int>,
    val text: String
) : SearchHint

@Serializable
data class SubcategoryHint(
    val imageLink: String,
    val title: String,
    val subtitle: String,
    val categoryObject: ProductCategory
) : SearchHint
