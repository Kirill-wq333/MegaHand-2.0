package com.evothings.data.feature.product.datasource

import com.evothings.data.feature.product.dto.response.ProductResponse
import com.evothings.data.network.annotation.WithAuthorization
import com.evothings.data.network.config.NetworkConfig
import com.evothings.domain.feature.product.model.PagedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ProductApi {

    @WithAuthorization
    @GET("${NetworkConfig.Routes.Product.list}{id}")
    fun getById(@Path("id") id: Int): Call<ProductResponse>

    @WithAuthorization
    @GET
    fun getList(@Url link: String): Call<PagedResponse<ProductResponse>>

}