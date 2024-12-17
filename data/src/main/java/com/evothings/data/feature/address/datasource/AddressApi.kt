package com.evothings.data.feature.address.datasource

import com.evothings.data.feature.address.dto.AddressDataModel
import com.evothings.data.feature.address.dto.PrimaryAddressRequest
import com.evothings.data.network.annotation.WithAuthorization
import com.evothings.data.network.config.NetworkConfig
import com.evothings.domain.feature.product.model.PagedResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AddressApi {

    @WithAuthorization
    @GET(NetworkConfig.Routes.Address.root)
    fun getAddressList(): Call<PagedResponse<AddressDataModel>>

    @WithAuthorization
    @POST(NetworkConfig.Routes.Address.root)
    fun createAddress(@Body payload: AddressDataModel): Call<Unit>

    @WithAuthorization
    @PATCH("${NetworkConfig.Routes.Address.root}{id}/")
    fun editAddress(@Path("id") id: Int, @Body payload: AddressDataModel): Call<Unit>

    @WithAuthorization
    @PATCH("${NetworkConfig.Routes.Address.root}{id}/")
    fun setAddressPrimary(@Path("id") id: Int, @Body payload: PrimaryAddressRequest): Call<Unit>

    @WithAuthorization
    @DELETE("${NetworkConfig.Routes.Address.root}{id}/")
    suspend fun deleteAddress(@Path("id") id: Int): Response<Unit>

}