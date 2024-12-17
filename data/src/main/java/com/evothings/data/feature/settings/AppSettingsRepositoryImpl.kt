package com.evothings.data.feature.settings

import com.evothings.data.storage.dataStore.DataStoreClient
import com.evothings.domain.feature.settings.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AppSettingsRepositoryImpl(
    private val dataStoreClient: DataStoreClient
) : AppSettingsRepository {

    companion object {
        private const val DARK_THEME_KEY = "darkTheme"
        private const val DEV_MODE_KEY = "devMode"
        private const val INTRO_IS_SHOWN_KEY = "introIsShown"
        private const val CITY_KEY = "city"
    }

    override val isDarkThemeEnabled: Flow<Boolean>
        get() = dataStoreClient.getBoolean(DARK_THEME_KEY)

    override val city: Flow<String>
        get() = dataStoreClient.getString(CITY_KEY)

    override suspend fun toggleDevMode() {
        val curValue = dataStoreClient.getBoolean(DEV_MODE_KEY).first()
        dataStoreClient.setBoolean(DEV_MODE_KEY, !curValue)
    }

    override suspend fun changeTheme() {
        val curValue = dataStoreClient.getBoolean(DARK_THEME_KEY).first()
        dataStoreClient.setBoolean(DARK_THEME_KEY, !curValue)
    }

    override suspend fun disableOnboarding(key: String) =
        dataStoreClient.setBoolean(key, false)

    override suspend fun setIntroScreenIsShown() {
        dataStoreClient.setBoolean(INTRO_IS_SHOWN_KEY, true)
    }

    override suspend fun disableAllOnboarding() {
        val onboardingKeys =
            setOf("mainOnboarding", "cardOnboarding", "shopsOnboarding", "profileOnboarding")
        onboardingKeys.forEach { key ->
            disableOnboarding(key)
        }
    }

    override suspend fun isOnboardingActive(key: String): Boolean =
        dataStoreClient.getBoolean(key, true).first()

    override suspend fun isDevModeEnabled(): Boolean =
        dataStoreClient.getBoolean(DEV_MODE_KEY).first()

    override suspend fun isIntroScreenShown(): Boolean =
        dataStoreClient.getBoolean(INTRO_IS_SHOWN_KEY).first()

    override suspend fun setCity(city: String) {
        dataStoreClient.setString(CITY_KEY, city)
    }


}