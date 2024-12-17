package com.evothings.domain.feature.catalog.repository

import com.evothings.domain.feature.catalog.model.FilterValue
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.catalog.model.SearchHint

interface CatalogRepository {

    suspend fun getCategories(): Result<List<ProductCategory>>

    suspend fun loadSearchHints(query: String): Result<List<SearchHint>>

    suspend fun getFilterValues(): Map<String, List<FilterValue>>

}