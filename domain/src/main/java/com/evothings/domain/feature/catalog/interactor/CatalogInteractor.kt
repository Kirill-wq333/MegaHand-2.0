package com.evothings.domain.feature.catalog.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.catalog.repository.CatalogRepository
import com.evothings.domain.feature.product.model.SearchFilter
import com.evothings.domain.feature.product.paging.ProductsPagingSource
import com.evothings.domain.feature.product.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope

class CatalogInteractor(
    private val repository: CatalogRepository,
    private val productRepository: ProductRepository
) {

    fun getPagingProducts(
        searchQuery: String?,
        category: ProductCategory?,
        propertyFilters: Map<String, List<Int>>,
        scope: CoroutineScope
    ) = Pager(
            config = PagingConfig(pageSize = 20),
            initialKey = 0,
            pagingSourceFactory = {
                ProductsPagingSource(
                    productRepository = productRepository,
                    searchFilter = SearchFilter(
                        q = searchQuery,
                        category = category?.id,
                        propertyFilters = propertyFilters
                    )
                )
            }
        ).flow.cachedIn(scope)

    suspend fun getProductsCount(
        searchQuery: String?,
        category: ProductCategory?,
        propertyFilters: Map<String, List<Int>>
    ): Int {
        val searchFilter = SearchFilter(
            q = searchQuery,
            category = category?.id,
            propertyFilters = propertyFilters
        )
        return productRepository.getProductsCount(searchFilter)
    }


    suspend fun getAllCategories() = repository.getCategories()

    suspend fun getFilters() = repository.getFilterValues()

    suspend fun getSearchHints(query: String) = repository.loadSearchHints(query)

}