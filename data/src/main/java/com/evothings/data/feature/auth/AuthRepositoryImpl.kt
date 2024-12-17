package com.evothings.data.feature.auth

import com.evothings.data.feature.auth.datasource.AuthNetworkClient
import com.evothings.data.feature.auth.dto.mapper.getSecureCodeRequest
import com.evothings.data.feature.auth.dto.request.PostCodeBody
import com.evothings.data.feature.auth.dto.request.RequestCodeBody
import com.evothings.data.feature.auth.dto.request.RequestSmsBody
import com.evothings.data.feature.auth.dto.response.AuthTokensResponse
import com.evothings.data.feature.auth.storageManager.UnauthorizedStorageManager
import com.evothings.data.storage.room.dao.TokenStoreDao
import com.evothings.data.storage.room.entity.auth.TokenEntity
import com.evothings.data.utils.awaitResult
import com.evothings.data.utils.date.expirationToTimestamp
import com.evothings.domain.feature.auth.repository.AuthRepository

class AuthRepositoryImpl(
    private val networkClient: AuthNetworkClient,
    private val unauthorizedStorageManager: UnauthorizedStorageManager,
    private val tokenDao: TokenStoreDao
) : AuthRepository {

    override suspend fun isLoggedIn(): Boolean =
        tokenDao.getTokenEntity() != null

    override suspend fun requestCode(phone: String, refCode: String): Result<Boolean> =
        networkClient.requestAuthCode(body = RequestCodeBody(phone, refCode))
            .awaitResult()
            .mapCatching { it.create }


    override suspend fun resendCode(phone: String) =
        networkClient.requestAuthCode(body = RequestCodeBody(phone, ""))
            .awaitResult()
            .map {}


    override suspend fun checkCode(phone: String, code: String): Result<Unit> =
        networkClient.checkAuthCode(body = PostCodeBody(phone, code.toInt()))
            .awaitResult()
            .onSuccess { response ->
                insertTokens(response)
                unauthorizedStorageManager.addStorageItems()
            }
            .mapCatching {}

    override suspend fun requestSmsCode(phone: String) =
        networkClient.requestSms(body = RequestSmsBody(phone)).awaitResult()

    override suspend fun setSecureCode(code: String, phone: String): Result<Unit> = runCatching {
        networkClient.setAccountSecureCode(getSecureCodeRequest(code, phone))
        unauthorizedStorageManager.addStorageItems()
    }

    override suspend fun loginBySecureCode(code: String, phone: String): Boolean {
        val result = networkClient.loginBySecureCode(getSecureCodeRequest(code, phone))
            .awaitResult()
            .onSuccess { response ->
                insertTokens(response)
                unauthorizedStorageManager.addStorageItems()
            }
        return result.isSuccess
    }

    private suspend fun insertTokens(response: AuthTokensResponse) {
        val accessExpirationMillis = expirationToTimestamp(response.accessExpirationDate)
        val refreshExpirationMillis = expirationToTimestamp(response.refreshExpireDate)

        tokenDao.insertToken(
            TokenEntity(
                accessToken = response.access,
                refreshToken = response.refresh,
                accessExpiration = accessExpirationMillis,
                refreshExpiration = refreshExpirationMillis
            )
        )
    }

}