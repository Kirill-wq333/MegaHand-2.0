package com.evothings.data.feature.auth.datasource

import com.evothings.data.feature.auth.dto.request.SecureCodeRequest
import com.evothings.data.feature.auth.dto.request.PostCodeBody
import com.evothings.data.feature.auth.dto.request.RefreshTokenRequest
import com.evothings.data.feature.auth.dto.response.AuthTokensResponse
import com.evothings.data.feature.auth.dto.request.RequestCodeBody
import com.evothings.data.feature.auth.dto.request.RequestSmsBody
import com.evothings.data.feature.auth.dto.response.token.AccessTokenResponse
import com.evothings.data.feature.auth.dto.response.token.RefreshTokenResponse
import com.evothings.data.feature.auth.dto.response.ShouldCreateUserResponse
import com.evothings.data.network.annotation.WithAuthorization
import com.evothings.data.network.config.NetworkConfig
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthNetworkClient {

    @POST(NetworkConfig.Routes.Auth.getCode)
    fun requestAuthCode(@Body body: RequestCodeBody): Call<ShouldCreateUserResponse>

    @POST(NetworkConfig.Routes.Auth.requestSMS)
    fun requestSms(@Body body: RequestSmsBody): Call<Unit>

    @POST(NetworkConfig.Routes.Auth.confirmCode)
    fun checkAuthCode(@Body body: PostCodeBody): Call<AuthTokensResponse>

    @POST(NetworkConfig.Routes.Auth.SecureCode.loginBy)
    fun loginBySecureCode(@Body body: SecureCodeRequest): Call<AuthTokensResponse>

    @POST(NetworkConfig.Routes.Auth.refreshToken)
    fun refreshToken(@Body body: RefreshTokenRequest): Call<AccessTokenResponse>

    @POST(NetworkConfig.Routes.Auth.newRefreshToken)
    fun getNewRefreshToken(@Body body: RefreshTokenRequest): Call<RefreshTokenResponse>

    @WithAuthorization
    @POST(NetworkConfig.Routes.Auth.SecureCode.set)
    suspend fun setAccountSecureCode(@Body code: SecureCodeRequest): Response<Unit>



}