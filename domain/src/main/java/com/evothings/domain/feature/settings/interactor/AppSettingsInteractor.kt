package com.evothings.domain.feature.settings.interactor

import com.evothings.domain.feature.settings.repository.AppSettingsRepository

class AppSettingsInteractor(private val repository: AppSettingsRepository) {

    val isDarkThemeEnabled = repository.isDarkThemeEnabled
    val city = repository.city

    suspend fun changeTheme() = repository.changeTheme()

    suspend fun toggleDevMode() = repository.toggleDevMode()

    suspend fun isDevModeEnabled() = repository.isDevModeEnabled()

    suspend fun setCity(city: String) = repository.setCity(city)

}