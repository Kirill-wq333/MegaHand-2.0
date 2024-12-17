package com.evothings.domain.feature.settings.repository

import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    val isDarkThemeEnabled: Flow<Boolean>
    val city: Flow<String>
    suspend fun toggleDevMode()
    suspend fun changeTheme()
    suspend fun setCity(city: String)
    suspend fun disableOnboarding(key: String)
    suspend fun setIntroScreenIsShown()
    suspend fun disableAllOnboarding()
    suspend fun isOnboardingActive(key: String): Boolean
    suspend fun isIntroScreenShown(): Boolean
    suspend fun isDevModeEnabled(): Boolean
}