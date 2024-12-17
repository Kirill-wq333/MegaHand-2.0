package com.evothings.data.feature.shops.datasource

import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.feature.shops.dto.ShopResponse
import com.evothings.domain.feature.product.model.PagedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopsNetworkClient {

    @GET(NetworkConfig.Routes.Main.shops)
    fun getShopsList(
        @Query("city") city: String
    ): Call<PagedResponse<ShopResponse>>

}