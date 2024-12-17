package com.evothings.data.feature.catalog

import com.evothings.data.feature.catalog.datasource.CatalogApi
import com.evothings.data.feature.catalog.dto.ProductCategoryResponse
import com.evothings.data.feature.catalog.dto.mapper.toFilterValue
import com.evothings.data.feature.catalog.dto.mapper.toProductCategory
import com.evothings.data.feature.product.dto.response.ProductResponse
import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.cache.fetchCache
import com.evothings.data.utils.awaitResult
import com.evothings.data.utils.fromJson
import com.evothings.data.utils.toJson
import com.evothings.domain.feature.catalog.model.FilterValue
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.catalog.model.SearchHint
import com.evothings.domain.feature.catalog.model.SubcategoryHint
import com.evothings.domain.feature.catalog.model.TextHint
import com.evothings.domain.feature.catalog.model.statical.filterKeys
import com.evothings.domain.feature.catalog.model.statical.sortingFilterValues
import com.evothings.domain.feature.catalog.repository.CatalogRepository

class CatalogRepositoryImpl(
    private val catalogApi: CatalogApi,
    private val cacheStore: CacheStore,
) : CatalogRepository {

    override suspend fun getCategories(): Result<List<ProductCategory>> =
        fetchCache(
            forceOnline = false,
            cacheKey = NetworkConfig.Routes.Catalog.categories,
            fetchFromNetwork = { catalogApi.getProductCategories().awaitResult() },
            cacheStore = cacheStore,
            mapper = {
                val withoutParent = results.filter { it.parentCategory == null }
                withoutParent.map { it.toProductCategory() }
            }
        )

    override suspend fun loadSearchHints(query: String): Result<List<SearchHint>> {
        return fetchCache(
            forceOnline = false,
            cacheStore = cacheStore,
            cacheKey = "searchHintsForQuery_$query",
            fetchFromNetwork = { catalogApi.performSearch(query, limit = 10).awaitResult() },
            mapper = {
                val response = this
                val textHints = getTextHints(response.products.toList(), query)
                val categoryHints = getCategoryHints(response.categories.toList())
                listOf(textHints, categoryHints).flatten()
            }
        )
    }

    private fun getTextHints(foundProducts: List<ProductResponse>, query: String): List<TextHint> {
        val hintsList: ArrayList<TextHint> = arrayListOf()
        for (product in foundProducts) {
            val selectionRange = findQueryInProductName(product.name, query)?.toList() ?: emptyList()
            hintsList.add(
                TextHint(
                    selectionRange = selectionRange,
                    text = product.name
                )
            )
        }
        return hintsList
    }

    private fun findQueryInProductName(name: String, query: String): IntRange? {
        val startIndex = name.indexOf(query)
        return if (startIndex != -1) {
            val endIndex = startIndex + query.lastIndex
            startIndex..endIndex
        } else null
    }

    private fun getCategoryHints(foundCategories: List<ProductCategoryResponse>): List<SubcategoryHint> =
        foundCategories.map { response ->
            SubcategoryHint(
                imageLink = response.photoLink.orEmpty(),
                title = response.title,
                subtitle = getCategoryParents(response).joinToString(" - "),
                categoryObject = response.toProductCategory()
            )
        }

    private fun getCategoryParents(categoryResponse: ProductCategoryResponse): List<String> {
        val parentNames = arrayListOf<String>()
        if (categoryResponse.parentCategory != null) {
            parentNames.add(categoryResponse.parentCategory.title)
            parentNames.addAll(getCategoryParents(categoryResponse.parentCategory))
        }
        return parentNames
    }


    override suspend fun getFilterValues(): Map<String, List<FilterValue>> {
        val filtersMap: HashMap<String, List<FilterValue>>  = hashMapOf()
        for (filterKey in filterKeys) {
            if (filterKey == "Сортировка") {
                filtersMap.put(filterKey, sortingFilterValues)
                continue
            }
            val filterValues = fetchFilterByKey(filterKey)
            filtersMap.put(filterKey, filterValues)
        }
        return filtersMap
    }

    private suspend fun fetchFilterByKey(key: String): List<FilterValue> {
        val cacheKey = "filter_$key"
        val cachedFilter = cacheStore.get(cacheKey)
        return if (cachedFilter == null) {
            val filterFromNetwork = catalogApi.getFilterValues(key).awaitResult()
            filterFromNetwork
                .mapCatching { response -> response.results.map { it.toFilterValue() } }
                .onSuccess { filters ->
                    val jsonFilters = filters.toJson()
                    cacheStore.put(cacheKey, jsonFilters)
                }
                .getOrDefault(emptyList())
        } else {
            cachedFilter.fromJson<List<FilterValue>>()
        }
    }

}