package com.evothings.data.feature.splash

import com.evothings.data.feature.splash.datasource.SplashNetworkClient
import com.evothings.data.feature.splash.dto.SettingsDto
import com.evothings.data.feature.splash.dto.map
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.splash.ApplicationStatus
import com.evothings.domain.feature.splash.SplashRepository

class SplashRepositoryImpl(
    private val networkClient: SplashNetworkClient
) : SplashRepository {

    override suspend fun fetchAppStatus(): Result<ApplicationStatus> =
        networkClient.getAppSettings().awaitResult().mapCatching(SettingsDto::map)


}