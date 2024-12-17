package com.evothings.data.feature.coupon.datasource

import com.evothings.data.feature.coupon.dto.CouponRequestDto
import com.evothings.data.feature.coupon.dto.RewardResponse
import com.evothings.data.feature.coupon.dto.StatusResponse
import com.evothings.data.feature.profile.dto.request.ConfirmPhoneRequest
import com.evothings.data.network.annotation.WithAuthorization
import com.evothings.data.network.config.NetworkConfig
import com.evothings.domain.feature.product.model.PagedResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CouponApiClient {

    @WithAuthorization
    @GET(NetworkConfig.Routes.Coupon.isAvailable)
    fun getIsCouponAvailable(): Call<StatusResponse>

    @GET(NetworkConfig.Routes.Coupon.getReward)
    fun getCouponRewardAmount(
        @Query("limit") limit: Int = 0,
        @Query("offset") offset: Int = 0
    ): Call<PagedResponse<RewardResponse>>

    @POST(NetworkConfig.Routes.Coupon.getCode)
    fun requestCouponCode(@Body couponRequestDto: CouponRequestDto): Call<Unit>

    @POST(NetworkConfig.Routes.Coupon.confirmCode)
    fun confirmCouponCode(@Body confirmPhoneRequest: ConfirmPhoneRequest): Call<Unit>

}