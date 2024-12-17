package com.evothings.data.feature.home

import com.evothings.data.feature.home.datasource.HomeNetworkClient
import com.evothings.data.feature.home.dto.mapper.toBrand
import com.evothings.data.feature.home.dto.mapper.toStoriesList
import com.evothings.data.feature.home.dto.request.UserSurveyRequest
import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.cache.fetchCache
import com.evothings.data.utils.awaitResult
import com.evothings.data.utils.getAndTransform
import com.evothings.domain.feature.home.model.Brand
import com.evothings.domain.feature.home.model.Story
import com.evothings.domain.feature.home.repository.HomeRepository

class HomeRepositoryImpl(
    private val networkClient: HomeNetworkClient,
    private val cacheStore: CacheStore
) : HomeRepository {

    override suspend fun getStories(): Result<List<Story>> =
        fetchCache(
            forceOnline = false,
            cacheKey = NetworkConfig.Routes.Main.stories,
            fetchFromNetwork = { networkClient.getStories().awaitResult() },
            cacheStore = cacheStore,
            mapper = { this.results.toStoriesList() }
        )

    override suspend fun getBrands(): Result<List<Brand>> =
        fetchCache(
            forceOnline = false,
            cacheKey = NetworkConfig.Routes.Main.brands,
            fetchFromNetwork = { networkClient.getBrands().awaitResult() },
            cacheStore = cacheStore,
            mapper = { this.map { it.toBrand() } }
        )

    override suspend fun submitUserSurvey(itemIndex: Int): Result<Unit> =
        kotlin.runCatching {
            networkClient.submitSurvey(survey = UserSurveyRequest(itemIndex))
        }

    override suspend fun transliterateCityName(city: String): Result<String> =
        getAndTransform(
            transform = { this.city ?: "Москва" }
        ) {
            networkClient.transliterateCity(city = city)
        }


}