package com.evothings.data.feature.splash.datasource

import com.evothings.data.feature.splash.dto.SettingsDto
import com.evothings.data.network.config.NetworkConfig
import retrofit2.Call
import retrofit2.http.GET

interface SplashNetworkClient {

    @GET(NetworkConfig.Routes.Splash.appSettings)
    fun getAppSettings(): Call<SettingsDto>

}