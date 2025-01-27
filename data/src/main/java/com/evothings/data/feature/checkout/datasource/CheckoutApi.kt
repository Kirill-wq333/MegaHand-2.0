package com.evothings.data.feature.checkout.datasource

import com.evothings.data.feature.checkout.dto.response.CheckoutData
import com.evothings.data.feature.checkout.dto.request.AddressRequest
import com.evothings.data.feature.checkout.dto.request.AmountRequest
import com.evothings.data.feature.checkout.dto.request.DeliveryPriceRequest
import com.evothings.data.feature.checkout.dto.request.PointsDiscountRequest
import com.evothings.data.feature.checkout.dto.response.AmountResponse
import com.evothings.data.feature.checkout.dto.response.DeliveryPriceResponse
import com.evothings.data.feature.checkout.dto.response.PaymentLinkResponse
import com.evothings.data.feature.checkout.dto.response.PriceDetailsResponse
import com.evothings.data.feature.checkout.dto.response.cdek.CdekPointsResponse
import com.evothings.data.network.annotation.WithAuthorization
import com.evothings.data.network.config.NetworkConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CheckoutApi {

    @WithAuthorization
    @GET("${NetworkConfig.Routes.Order.order}{id}/")
    fun getOrderCheckoutData(@Path("id") id: String): Call<CheckoutData>

    @WithAuthorization
    @PATCH("${NetworkConfig.Routes.Order.order}{id}/")
    fun updateCheckoutOrder(@Path("id") id: String, @Body payload: CheckoutData): Call<PaymentLinkResponse>

    @WithAuthorization
    @POST("${NetworkConfig.Routes.Order.order}{id}/calculate-delivery-cost/")
    fun calculateDeliveryCost(
        @Path("id") id: String,
        @Body payload: DeliveryPriceRequest
    ): Call<DeliveryPriceResponse>

    @WithAuthorization
    @POST("${NetworkConfig.Routes.Order.order}{id}/prices/")
    fun calculatePointsDiscount(
        @Path("id") id: String,
        @Body payload: PointsDiscountRequest
    ): Call<PriceDetailsResponse>

    @WithAuthorization
    @POST(NetworkConfig.Routes.Order.getCdekPickupPoints)
    fun getCdekPickupPoints(@Body payload: AddressRequest): Call<CdekPointsResponse>

    @WithAuthorization
    @POST("${NetworkConfig.NEW_BASE_URL}order/{id}/get-available-amount/")
    fun getAvailableCardBalance(
        @Path("id") id: String,
        @Body amount: AmountRequest
    ): Call<AmountResponse>

}