package com.evothings.domain.feature.favourites.interactor

import com.evothings.domain.feature.favourites.repository.FavouritesRepository

class FavouritesInteractor(private val repository: FavouritesRepository) {

    suspend fun getFavourites(categoryId: Int? = null) = repository.getFavourites(categoryId)

    suspend fun getProductCategories(productIds: List<Int>) = repository.getProductCategories(productIds)

    suspend fun removeFromFavourite(id: Int) = repository.removeFavourite(id)

    suspend fun clear() = repository.clear()

}