package com.evothings.domain.feature.shops.interactor

import com.evothings.domain.feature.shops.model.Shop
import com.evothings.domain.feature.shops.repository.ShopsRepository

class ShopsInteractor(private val shopsRepository: ShopsRepository) {

    suspend fun getShops(city: String): Result<List<Shop>> =
        shopsRepository.getShops(city)

}