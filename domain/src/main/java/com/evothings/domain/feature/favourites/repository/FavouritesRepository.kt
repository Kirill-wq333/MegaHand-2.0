package com.evothings.domain.feature.favourites.repository

import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.product.model.Product

interface FavouritesRepository {
    suspend fun getFavourites(categoryId: Int?): Result<List<Product>>
    suspend fun getProductCategories(products: List<Int>): Result<List<ProductCategory>>
    suspend fun addToFavourites(id: Int): Result<Unit>
    suspend fun removeFavourite(id: Int): Result<Unit>
    suspend fun clear(): Result<Unit>
}