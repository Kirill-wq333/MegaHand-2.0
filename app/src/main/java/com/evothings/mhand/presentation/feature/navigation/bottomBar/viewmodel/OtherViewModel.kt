package com.evothings.mhand.presentation.feature.navigation.bottombar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.coupon.interactor.CouponInteractor
import com.evothings.domain.feature.onboarding.interactor.OnboardingInteractor
import com.evothings.domain.feature.settings.interactor.AppSettingsInteractor
import com.evothings.mhand.presentation.feature.onboarding.model.OnboardingCacheKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherViewModel @Inject constructor(
    private val onboardingInteractor: OnboardingInteractor,
    private val appSettingsInteractor: AppSettingsInteractor,
    private val couponInteractor: CouponInteractor
) : ViewModel() {

    val isDarkThemeEnabled: Flow<Boolean>
        get() = appSettingsInteractor.isDarkThemeEnabled

    private val _isCouponAvailable = MutableStateFlow(false)
    val isCouponGatheringAvailable = _isCouponAvailable.asStateFlow()

    private val _otherOnboardingEnabled = MutableStateFlow(true)
    val otherOnboardingEnabled = _otherOnboardingEnabled.asStateFlow()

    private val _couponAmount = MutableStateFlow(0)
    val couponAmount = _couponAmount.asStateFlow()

    init {
        checkIsCouponAvailable()
        checkIsOtherOnboardingEnabled()
    }

    private fun checkIsCouponAvailable() {
        viewModelScope.launch(Dispatchers.IO) {
            val isAvailable =
                couponInteractor.isCouponAvailableForUser().getOrDefault(false)
            if (isAvailable) {
                val bonusAmount = couponInteractor.getCouponAmount()
                _couponAmount.emit(bonusAmount)
            }
            _isCouponAvailable.emit(isAvailable)
        }
    }

    private fun checkIsOtherOnboardingEnabled() {
        viewModelScope.launch(Dispatchers.IO) {
            val isEnabled = onboardingInteractor.isOnboardingActive(OnboardingCacheKey.OTHER)
            _otherOnboardingEnabled.emit(isEnabled)
        }
    }

    fun changeTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            appSettingsInteractor.changeTheme()
        }
    }

    fun finishOnboarding() {
        viewModelScope.launch(Dispatchers.IO) {
            onboardingInteractor.disableOnboarding(OnboardingCacheKey.OTHER)
            _otherOnboardingEnabled.emit(false)
        }
    }

}