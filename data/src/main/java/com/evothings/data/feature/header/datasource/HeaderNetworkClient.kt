package com.evothings.data.feature.header.datasource

import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.feature.header.dto.CityDto
import retrofit2.Call
import retrofit2.http.GET

interface HeaderNetworkClient {

    @GET(NetworkConfig.Routes.Header.cities)
    fun getCities(): Call<Array<CityDto>>

}