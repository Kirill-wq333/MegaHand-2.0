package com.evothings.data.feature.auth.storageManager

import com.evothings.data.feature.cart.datasource.CartApi
import com.evothings.data.feature.favourites.datasource.FavouritesApiClient
import com.evothings.data.feature.product.dto.mapper.toProductIdRequest
import com.evothings.data.storage.room.dao.ProductsDao
import com.evothings.data.utils.awaitResult
import javax.inject.Inject

class UnauthorizedStorageManager @Inject constructor(
    private val productsDao: ProductsDao,
    private val cartApi: CartApi,
    private val favouritesApi: FavouritesApiClient
) {

    suspend fun addStorageItems() {
        runCatching {
            addStorageCartItems()
            addStorageFavouriteItems()
        }
        flushStorage()
    }

    private suspend fun addStorageCartItems() {
        val favourites = productsDao.getFavourites()
        for (productId in favourites) {
            favouritesApi.addToFavourites(productId.toProductIdRequest()).awaitResult()
        }
    }

    private suspend fun addStorageFavouriteItems() {
        val cartItems = productsDao.getCartItems()
        for (productId in cartItems) {
            cartApi.addToCart(productId.toProductIdRequest()).awaitResult()
        }
    }

    private suspend fun flushStorage() {
        productsDao.clear()
    }

}