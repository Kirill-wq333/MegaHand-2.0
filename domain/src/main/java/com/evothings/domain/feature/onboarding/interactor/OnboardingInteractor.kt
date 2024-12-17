package com.evothings.domain.feature.onboarding.interactor

import com.evothings.domain.feature.settings.repository.AppSettingsRepository

class OnboardingInteractor(private val appSettingsRepository: AppSettingsRepository) {

    suspend fun isOnboardingActive(key: String) = appSettingsRepository.isOnboardingActive(key)

    suspend fun disableOnboarding(key: String) = appSettingsRepository.disableOnboarding(key)

    suspend fun disableAllOnboardings() = appSettingsRepository.disableAllOnboarding()

    suspend fun isIntroScreenShown() = appSettingsRepository.isIntroScreenShown()

    suspend fun setIntroScreenShown() = appSettingsRepository.setIntroScreenIsShown()

}