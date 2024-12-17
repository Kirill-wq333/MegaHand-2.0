package com.evothings.data.feature.profile.datasource

import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.feature.profile.dto.request.ConfirmPhoneRequest
import com.evothings.data.feature.profile.dto.response.referals.ReferalsResponse
import com.evothings.data.feature.profile.dto.request.UpdatePhoneRequest
import com.evothings.data.feature.profile.dto.request.UpdateProfileRequest
import com.evothings.data.feature.profile.dto.response.order.OrderResponse
import com.evothings.data.feature.profile.dto.response.ProfileResponse
import com.evothings.data.feature.profile.dto.response.StatusMessageResponse
import com.evothings.data.network.annotation.WithAuthorization
import com.evothings.domain.feature.product.model.PagedResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ProfileNetworkClient {

    @WithAuthorization
    @DELETE(NetworkConfig.Routes.Profile.deleteAccount)
    suspend fun deleteProfile()

    @WithAuthorization
    @GET(NetworkConfig.Routes.Profile.getReferals)
    fun getUserReferals(): Call<ReferalsResponse>

    @WithAuthorization
    @GET(NetworkConfig.Routes.Profile.ordersHistory)
    fun getOrdersHistory(): Call<PagedResponse<OrderResponse>>

    @WithAuthorization
    @GET(NetworkConfig.Routes.Profile.clientInfo)
    fun getClientInfo(): Call<ProfileResponse>

    @WithAuthorization
    @PATCH(NetworkConfig.Routes.Profile.editProfile)
    suspend fun updateProfile(@Body updateProfileRequest: UpdateProfileRequest): Response<StatusMessageResponse>

    @WithAuthorization
    @POST(NetworkConfig.Routes.Profile.confirmChangedPhone)
    suspend fun confirmNewPhone(@Body confirmPhoneRequest: ConfirmPhoneRequest): Response<StatusMessageResponse>

    @WithAuthorization
    @POST(NetworkConfig.Routes.Profile.updateUserPhone)
    fun sendCodeOnNewPhone(@Body updatePhoneRequest: UpdatePhoneRequest): Call<Unit>

    @WithAuthorization
    @GET(NetworkConfig.Routes.Notifications.unsubscribeDevice)
    suspend fun unsubscribeDevice()

}