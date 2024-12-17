package com.evothings.data.feature.header

import com.evothings.data.feature.header.datasource.HeaderNetworkClient
import com.evothings.data.feature.header.dto.CityDto
import com.evothings.data.feature.header.dto.mapper.toCitiesMap
import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.cache.fetchCache
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.home.model.CitiesMap
import com.evothings.domain.feature.header.repository.HeaderRepository

class HeaderRepositoryImpl(
    private val networkClient: HeaderNetworkClient,
    private val cacheStore: CacheStore
) : HeaderRepository {

    override suspend fun getCitiesList(): Result<CitiesMap> =
        fetchCache(
            forceOnline = false,
            cacheStore = cacheStore,
            fetchFromNetwork = { networkClient.getCities().awaitResult() },
            cacheKey = NetworkConfig.Routes.Header.cities,
            mapper = Array<CityDto>::toCitiesMap,
        )

}