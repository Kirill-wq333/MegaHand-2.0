package com.evothings.data.feature.product

import com.evothings.data.feature.product.datasource.ProductApi
import com.evothings.data.feature.product.dto.mapper.toProduct
import com.evothings.data.feature.product.dto.response.ProductResponse
import com.evothings.data.feature.product.util.buildFilterQueryParameters
import com.evothings.data.feature.product.util.modifyIfPresentInStorage
import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.cache.fetchCache
import com.evothings.data.storage.room.dao.ProductsDao
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.auth.repository.AuthRepository
import com.evothings.domain.feature.product.model.PagedResponse
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.feature.product.model.SearchFilter
import com.evothings.domain.feature.product.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    private val productApi: ProductApi,
    private val authRepository: AuthRepository,
    private val productsDao: ProductsDao,
    private val cacheStore: CacheStore
) : ProductRepository {

    override suspend fun getInfoById(id: Int, force: Boolean): Result<Product> {
        val productResponse = fetchCache(
            forceOnline = force,
            cacheKey = id.toString(),
            fetchFromNetwork = { productApi.getById(id).awaitResult() },
            cacheStore = cacheStore,
            mapper = ProductResponse::toProduct
        )
        return productResponse.mapCatching { it.modifyIfInStorage() }
    }

    private suspend fun Product.modifyIfInStorage(): Product {
        if (authRepository.isLoggedIn()) return this
        val cartItems = productsDao.getCartItems()
        val favouriteItems = productsDao.getFavourites()
        return this.modifyIfPresentInStorage(cartItems, favouriteItems)
    }

    override suspend fun getNewProducts(): Result<List<Product>> {
        val filters = SearchFilter(isNew = true, limit = 4)
        return runCatching { getProductsPaging(filters, offset = 0).results.toList() }
    }

    override suspend fun getProductsCount(filter: SearchFilter): Int {
        return runCatching {
            getProductsPaging(filter, offset = 0).count
        }.getOrDefault(0)
    }

    override suspend fun getProductsPaging(
        filter: SearchFilter,
        offset: Int
    ): PagedResponse<Product> = withContext(Dispatchers.IO) {
        val listUrl = NetworkConfig.Routes.Product.list
        val preparedFilters = filter.prepareToRequest(pagingOffset = offset)
        val queryParameters = buildFilterQueryParameters(preparedFilters)
        val link = listUrl + queryParameters

        val productsPortion = productApi.getList(link).awaitResult().getOrThrow()
        var products = productsPortion.results.map { it.toProduct() }

        if (!authRepository.isLoggedIn()) {
            products = products.modifyIfPresentInStorage(productsDao)
        }

        PagedResponse(
            count = productsPortion.count,
            next = productsPortion.next,
            previous = productsPortion.previous,
            results = products.toTypedArray()
        )
    }

    private fun SearchFilter.prepareToRequest(pagingOffset: Int): SearchFilter {
        val filter = this
        val withEnglishKeys = filter.copy(
            propertyFilters = filter.propertyFilters.withEnglishKeys()
        )
        val orderingAsParameter = withEnglishKeys.withOrderingAsParameter()
        return orderingAsParameter.copy(offset = pagingOffset, limit = 20)
    }

    private fun SearchFilter.withOrderingAsParameter(): SearchFilter {
        val mutPropertyFilters = this.propertyFilters.toMutableMap()
        val orderingValue = mutPropertyFilters["ordering"]?.firstOrNull() ?: return this
        mutPropertyFilters.remove("ordering")
        return this.copy(
            propertyFilters = mutPropertyFilters,
            showExpensiveFirst = orderingValue == -2
        )
    }

    private fun Map<String, List<Int>>.withEnglishKeys(): Map<String, List<Int>> {
        val newMap = hashMapOf<String, List<Int>>()
        for ((key, value) in this.entries) {
            val englishKey = key.toEnglishFilterKey()
            if (englishKey.isEmpty()) continue
            newMap.put(englishKey, value)
        }
        return newMap
    }

    private fun String.toEnglishFilterKey(): String {
        return when(this) {
            "Сортировка" -> "ordering"
            "Цвет" -> "color"
            "Материал" -> "material"
            "Размер" -> "size"
            "Качество" -> "quality"
            else -> ""
        }
    }

}