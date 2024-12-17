package com.evothings.data.feature.favourites

import com.evothings.data.feature.catalog.dto.mapper.toProductCategory
import com.evothings.data.feature.favourites.datasource.FavouritesApiClient
import com.evothings.data.feature.product.dto.mapper.toProduct
import com.evothings.data.feature.product.dto.mapper.toProductIdRequest
import com.evothings.data.feature.product.util.modifyIfPresentInStorage
import com.evothings.data.storage.room.dao.ProductsDao
import com.evothings.data.utils.awaitResult
import com.evothings.data.utils.foldAuthorized
import com.evothings.data.utils.getAndTransform
import com.evothings.domain.feature.auth.repository.AuthRepository
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.favourites.repository.FavouritesRepository
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.feature.product.repository.ProductRepository

class FavouritesRepositoryImpl(
    private val networkClient: FavouritesApiClient,
    private val productRepository: ProductRepository,
    private val productsDao: ProductsDao,
    private val authRepository: AuthRepository
) : FavouritesRepository {

    override suspend fun getFavourites(categoryId: Int?): Result<List<Product>> =
        foldAuthorized(
            checkIsLoggedIn = { authRepository.isLoggedIn() },
            onAuthorized = { getAuthorizedFavourites(categoryId) },
            onNotAuthorized = { getUnauthorizedFavourites() }
        )

    private suspend fun getAuthorizedFavourites(categoryId: Int?): Result<List<Product>> =
        getAndTransform(
            block = { networkClient.getFavouritesList(categoryId) },
            transform = {
                this.map { productResponse -> productResponse.toProduct() }
            }
        )

    private suspend fun getUnauthorizedFavourites(): Result<List<Product>> =
        runCatching {
            val favouriteProductIds = productsDao.getFavourites()
            val favourites = arrayListOf<Product>()
            for (id in favouriteProductIds) {
                productRepository.getInfoById(id)
                    .onSuccess { favourites.add(it) }
            }
            val modified = favourites.modifyIfPresentInStorage(productsDao)
            modified
        }

    override suspend fun getProductCategories(products: List<Int>): Result<List<ProductCategory>> {
        return foldAuthorized(
            checkIsLoggedIn = { authRepository.isLoggedIn() },
            onAuthorized = { getProductCategoriesAsAuthorized(products) },
            onNotAuthorized = { Result.success(emptyList()) }
        )
    }

    private suspend fun getProductCategoriesAsAuthorized(products: List<Int>): Result<List<ProductCategory>> {
        val productIdRequestArray = products.map { it.toProductIdRequest() }.toTypedArray()
        return networkClient.getProductCategories(productIdRequestArray).awaitResult()
            .mapCatching { response ->
                response.map { it.toProductCategory() }
            }
    }

    override suspend fun addToFavourites(id: Int): Result<Unit> {
        return foldAuthorized(
            checkIsLoggedIn = { authRepository.isLoggedIn() },
            onAuthorized = { networkClient.addToFavourites(id.toProductIdRequest()).awaitResult() },
            onNotAuthorized = {
                runCatching { productsDao.appendFavourite(id) }
            }
        )
    }

    override suspend fun removeFavourite(id: Int): Result<Unit> {
        return foldAuthorized(
            checkIsLoggedIn = { authRepository.isLoggedIn() },
            onAuthorized = { networkClient.removeFromFavourites(id.toProductIdRequest()).awaitResult() },
            onNotAuthorized = {
                runCatching { productsDao.removeFromFavourite(id) }
            }
        )
    }

    override suspend fun clear(): Result<Unit> {
        return foldAuthorized(
            checkIsLoggedIn = { authRepository.isLoggedIn() },
            onAuthorized = {
                runCatching { networkClient.flushFavourites() }.mapCatching {}
            },
            onNotAuthorized = {
                runCatching { productsDao.setFavourites(emptyList()) }
            }
        )
    }

}