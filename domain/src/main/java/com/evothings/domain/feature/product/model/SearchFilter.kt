package com.evothings.domain.feature.product.model

data class SearchFilter(
    val showExpensiveFirst: Boolean? = null,
    val propertyFilters: Map<String, List<Int>> = emptyMap(),
    val category: Int? = null,
    val q: String? = null,
    val isNew: Boolean? = null,
    val limit: Int = 0,
    val offset: Int = 0
)
