package com.evothings.domain.feature.product.model

data class PagedResponse<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: Array<T>
)
