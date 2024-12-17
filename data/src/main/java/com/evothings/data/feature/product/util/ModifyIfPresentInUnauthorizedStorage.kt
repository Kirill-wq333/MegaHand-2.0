package com.evothings.data.feature.product.util

import com.evothings.data.storage.room.dao.ProductsDao
import com.evothings.domain.feature.product.model.Product

internal suspend fun List<Product>.modifyIfPresentInStorage(storageDao: ProductsDao): List<Product> =
    runCatching {
        val list = this
        val cartItems = storageDao.getCartItems()
        val favouriteItems = storageDao.getFavourites()
        list.map { it.modifyIfPresentInStorage(cartItems, favouriteItems) }
    }.getOrDefault(this)

internal fun Product.modifyIfPresentInStorage(cartItems: List<Int>, favourites: List<Int>): Product {
    var newProduct = this
    if (newProduct.id in cartItems) {
        newProduct = newProduct.copy(isInCart = true)
    }
    if (newProduct.id in favourites) {
        newProduct = newProduct.copy(isFavourite = true)
    }
    return newProduct
}