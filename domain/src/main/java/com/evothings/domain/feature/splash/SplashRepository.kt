package com.evothings.domain.feature.splash


interface SplashRepository {
    suspend fun fetchAppStatus(): Result<ApplicationStatus>
}