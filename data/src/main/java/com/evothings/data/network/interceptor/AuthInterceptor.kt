package com.evothings.data.network.interceptor

import com.evothings.data.feature.auth.datasource.AuthNetworkClient
import com.evothings.data.feature.auth.dto.request.RefreshTokenRequest
import com.evothings.data.feature.auth.dto.response.token.TokenResponse
import com.evothings.data.network.annotation.WithAuthorization
import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.storage.room.dao.TokenStoreDao
import com.evothings.data.utils.awaitResult
import com.evothings.data.utils.date.expirationToTimestamp
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Invocation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class AuthInterceptor(private val tokenDao: TokenStoreDao) : Interceptor {

    private val networkClient: AuthNetworkClient by lazy {

        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkConfig.NEW_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(AuthNetworkClient::class.java)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val invocation = originalRequest.tag(Invocation::class.java)
        val hasAuthorizationAnnotation =
            invocation?.method()?.getAnnotation(WithAuthorization::class.java) != null

        val newRequest = if (hasAuthorizationAnnotation) {
            val accessToken = runBlocking { getAccessToken() }
            if (accessToken != null) {
                originalRequest.newBuilder()
                    .header("Authorization", "Bearer $accessToken")
                    .build()
            } else originalRequest
        } else originalRequest

        return chain.proceed(newRequest)
    }

    private suspend fun getAccessToken(): String? {
        val tokenEntity = tokenDao.getTokenEntity() ?: return null
        val accessExpirationInstant = Instant.ofEpochMilli(tokenEntity.accessExpiration)
        val isAccessExpired = accessExpirationInstant.isBefore(Instant.now())

        val isRefreshNearToExpire = isRefreshNearToExpire(tokenEntity.refreshExpiration)
        val refresh = if (isRefreshNearToExpire) {
            getNewRefreshToken(tokenEntity.refreshToken)
        } else {
            tokenEntity.refreshToken
        }

        return if (isAccessExpired) {
            getNewAccessToken(refresh ?: tokenEntity.refreshToken)
        } else tokenEntity.accessToken
    }

    private suspend fun getNewRefreshToken(refresh: String): String? {
        return getToken(
            fetchToken = { networkClient.getNewRefreshToken(RefreshTokenRequest(refresh)).awaitResult() },
            saveInDatabase = { response ->
                val expiration = expirationToTimestamp(response.expireDate)
                tokenDao.updateRefreshToken(response.token, expiration)
            }
        )
    }

    private suspend fun getNewAccessToken(refresh: String): String? {
        return getToken(
            fetchToken = { networkClient.refreshToken(RefreshTokenRequest(refresh)).awaitResult() },
            saveInDatabase = { response ->
                val expiration = expirationToTimestamp(response.expireDate)
                tokenDao.updateAccessToken(response.token, expiration)
            }
        )
    }

    private suspend inline fun getToken(
        fetchToken: suspend () -> Result<TokenResponse>,
        saveInDatabase: suspend (TokenResponse) -> Unit
    ): String? {
        val tokenFromNetwork = fetchToken()

        return tokenFromNetwork.fold(
            onSuccess = { response ->
                saveInDatabase(response)
                response.token
            },
            onFailure = { null }
        )
    }

    /*
     * Note: The refresh token is considered expired if it expires within one day
     */
    private fun isRefreshNearToExpire(expiration: Long): Boolean {
        val refreshExpireInstant = Instant.ofEpochMilli(expiration)
        val refreshLocalDateTime = LocalDateTime.ofInstant(refreshExpireInstant, ZoneId.systemDefault())
        val now = LocalDateTime.now()
        return refreshLocalDateTime.minusDays(1).isBefore(now)
    }

}