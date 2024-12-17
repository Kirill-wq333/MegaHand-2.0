package com.evothings.data.feature.captcha.datasource

import com.evothings.data.feature.captcha.dto.CaptchaResponse
import com.evothings.data.feature.captcha.dto.CaptchaToken
import com.evothings.data.network.config.NetworkConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CaptchaNetworkClient {

    @POST(NetworkConfig.Routes.Auth.verifyCaptcha)
    fun verifyCaptcha(@Body body: CaptchaToken): Call<CaptchaResponse>

}