package com.evothings.data.feature.profile

import com.evothings.data.feature.profile.datasource.ProfileNetworkClient
import com.evothings.data.feature.profile.dto.mapper.toOrdersList
import com.evothings.data.feature.profile.dto.mapper.toProfile
import com.evothings.data.feature.profile.dto.mapper.toReferalInfo
import com.evothings.data.feature.profile.dto.mapper.toUpdateRequest
import com.evothings.data.feature.profile.dto.request.ConfirmPhoneRequest
import com.evothings.data.feature.profile.dto.response.referals.ReferalsResponse
import com.evothings.data.feature.profile.dto.request.UpdatePhoneRequest
import com.evothings.data.feature.profile.dto.response.ErrorResponse
import com.evothings.data.feature.profile.dto.response.ProfileResponse
import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.cache.fetchCache
import com.evothings.data.storage.room.dao.CardDao
import com.evothings.data.storage.room.dao.TokenStoreDao
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.profile.model.Order
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.feature.profile.model.ReferalInfo
import com.evothings.domain.feature.profile.repository.ProfileRepository
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody

class ProfileRepositoryImpl(
    private val networkClient: ProfileNetworkClient,
    private val cacheStore: CacheStore,
    private val tokenDao: TokenStoreDao,
    private val cardDao: CardDao
) : ProfileRepository {

    override suspend fun getProfileInfo(forceOnline: Boolean): Result<Profile> =
        fetchCache(
            forceOnline = forceOnline,
            cacheKey = NetworkConfig.Routes.Profile.clientInfo,
            fetchFromNetwork = { networkClient.getClientInfo().awaitResult() },
            cacheStore = cacheStore,
            mapper = ProfileResponse::toProfile
        )

    override suspend fun getReferals(forceOnline: Boolean): Result<ReferalInfo> =
        fetchCache(
            forceOnline = forceOnline,
            cacheKey = NetworkConfig.Routes.Profile.getReferals,
            fetchFromNetwork = { networkClient.getUserReferals().awaitResult() },
            cacheStore = cacheStore,
            mapper = ReferalsResponse::toReferalInfo
        )

    override suspend fun getOrdersHistory(): Result<List<Order>> {
        return networkClient.getOrdersHistory().awaitResult()
            .mapCatching { it.results.toOrdersList() }
    }

    override suspend fun logout() {
        runCatching {
            cardDao.deleteCard()
            tokenDao.clear()
            networkClient.unsubscribeDevice()
        }
    }


    override suspend fun deleteAccount() {
        runCatching {
            cardDao.deleteCard()
            networkClient.deleteProfile()
            tokenDao.clear()
        }
    }

    override suspend fun editProfile(newProfile: Profile): Result<String?> =
        runCatching {
            val response = networkClient.updateProfile(newProfile.toUpdateRequest())
            cardDao.deleteCard()
            response.errorBody()?.fetchErrorMessage()
        }

    override suspend fun confirmChangeNumber(code: String, newPhone: String): Result<Boolean> =
        runCatching {
            networkClient.confirmNewPhone(
                ConfirmPhoneRequest(
                    phone = newPhone,
                    code = code.toInt()
                )
            ).isSuccessful
        }

    override suspend fun sendCodeOnNewPhone(phone: String): Result<Unit> =
        networkClient.sendCodeOnNewPhone(
            UpdatePhoneRequest(phone = phone)
        ).awaitResult()

    private fun ResponseBody.fetchErrorMessage(): String? =
        try {
            val jsonParser = Json { ignoreUnknownKeys = true }
            val responseJson = jsonParser.decodeFromString<ErrorResponse>(this.string())
            responseJson.error
        } catch(e: Exception) {
            null
        }

}