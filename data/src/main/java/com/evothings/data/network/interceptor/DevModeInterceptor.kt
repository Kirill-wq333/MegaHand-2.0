package com.evothings.data.network.interceptor

import com.evothings.domain.feature.settings.repository.AppSettingsRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class DevModeInterceptor(private val appSettingsRepository: AppSettingsRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestUrl = originalRequest.url
        if (requestUrl.host == "mhand.ru") {
            return chain.proceed(originalRequest)
        }

        val devModeEnabled = runBlocking { appSettingsRepository.isDevModeEnabled() }
        val newHost = (if (devModeEnabled) "testdev" else "dev") + ".avtovozina.ru"
        val urlWithPrefix = requestUrl.newBuilder().host(newHost).build()

        val updatedRequest = Request.Builder()
            .url(urlWithPrefix)
            .headers(originalRequest.headers)
            .method(originalRequest.method, originalRequest.body)
            .build()

        return chain.proceed(updatedRequest)
    }

}