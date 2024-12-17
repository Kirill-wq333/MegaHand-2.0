package com.evothings.domain.feature.product.repository

import com.evothings.domain.feature.product.model.PagedResponse
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.feature.product.model.SearchFilter

interface ProductRepository {

    suspend fun getInfoById(id: Int, force: Boolean = false): Result<Product>

    suspend fun getNewProducts(): Result<List<Product>>


    suspend fun getProductsCount(filter: SearchFilter): Int

    suspend fun getProductsPaging(filter: SearchFilter, offset: Int): PagedResponse<Product>

}