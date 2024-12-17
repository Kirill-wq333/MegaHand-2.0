package com.evothings.domain.feature.product.interactor

import com.evothings.domain.feature.cart.repository.CartRepository
import com.evothings.domain.feature.favourites.repository.FavouritesRepository
import com.evothings.domain.feature.product.repository.ProductRepository

class ProductInteractor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val favouritesRepository: FavouritesRepository
) {

    suspend fun toggleFavourite(id: Int): Result<Unit> {
        val isFavourite =
            productRepository.getInfoById(id, force = true).getOrNull()?.isFavourite ?: false

        return if (isFavourite) {
            favouritesRepository.removeFavourite(id)
        } else {
            favouritesRepository.addToFavourites(id)
        }
    }

    suspend fun toggleCart(id: Int): Result<Unit> {
        val isInCart =
            productRepository.getInfoById(id, force = true).getOrNull()?.isInCart ?: false

        return if (isInCart) {
            cartRepository.remove(id)
        } else {
            cartRepository.addProduct(id)
        }
    }


    suspend fun getProductById(id: Int, force: Boolean = false) =
        productRepository.getInfoById(id, force)

}