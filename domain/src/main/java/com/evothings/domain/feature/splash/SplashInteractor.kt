package com.evothings.domain.feature.splash

class SplashInteractor(private val repository: SplashRepository) {

    suspend fun fetchAppStatus() =
        repository.fetchAppStatus()

}