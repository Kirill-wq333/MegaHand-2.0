package com.evothings.data.feature.shops

import com.evothings.data.feature.shops.datasource.ShopsNetworkClient
import com.evothings.data.feature.shops.dto.mapper.toShopList
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.cache.fetchCache
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.shops.model.Shop
import com.evothings.domain.feature.shops.repository.ShopsRepository

class ShopsRepositoryImpl(
    private val networkClient: ShopsNetworkClient,
    private val cacheStore: CacheStore
) : ShopsRepository {

    override suspend fun getShops(city: String): Result<List<Shop>> =
        fetchCache(
            forceOnline = true,
            cacheStore = cacheStore,
            cacheKey = "shops_$city",
            fetchFromNetwork = { networkClient.getShopsList(city).awaitResult() },
            mapper = { this.results.toShopList() }
        )


}