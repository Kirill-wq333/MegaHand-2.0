package com.evothings.domain.feature.shops.repository

import com.evothings.domain.feature.shops.model.Shop

interface ShopsRepository {
    suspend fun getShops(city: String): Result<List<Shop>>
}