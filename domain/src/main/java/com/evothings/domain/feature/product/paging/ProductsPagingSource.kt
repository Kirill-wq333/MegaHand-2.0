package com.evothings.domain.feature.product.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.feature.product.model.SearchFilter
import com.evothings.domain.feature.product.repository.ProductRepository

class ProductsPagingSource(
    private val productRepository: ProductRepository,
    private val searchFilter: SearchFilter
) : PagingSource<Int, Product>() {

    override val keyReuseSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val offset = params.key ?: 0

            val products = productRepository.getProductsPaging(searchFilter, offset)

            val nextKey = if (products.next != null) offset + 20 else null

            LoadResult.Page(
                data = products.results.toList(),
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}