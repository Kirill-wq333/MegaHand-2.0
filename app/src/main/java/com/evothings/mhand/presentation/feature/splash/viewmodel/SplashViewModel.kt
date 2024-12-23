package com.evothings.mhand.presentation.feature.splash.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.settings.interactor.AppSettingsInteractor
import com.evothings.domain.feature.header.HeaderInteractor
import com.evothings.domain.feature.notification.interactor.NotificationInteractor
import com.evothings.domain.feature.onboarding.interactor.OnboardingInteractor
import com.evothings.domain.feature.profile.interactor.ProfileInteractor
import com.evothings.domain.feature.splash.SplashInteractor
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashInteractor: SplashInteractor,
    private val headerInteractor: HeaderInteractor,
    private val notificationInteractor: NotificationInteractor,
    private val profileInteractor: ProfileInteractor,
    private val appSettingsInteractor: AppSettingsInteractor,
    private val onboardingInteractor: OnboardingInteractor
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    override fun setInitialState(): SplashContract.State = SplashContract.State.Initial


    override fun handleEvent(event: SplashContract.Event) {
        when(event) {
            is SplashContract.Event.CheckApp -> {
                subscribeToMainNotificationTopic()
                checkUpdateAndDetermineNavRoute()
            }
        }
    }

    private fun subscribeToMainNotificationTopic() {
        Firebase.messaging.token.addOnSuccessListener { token ->
            viewModelScope.launch(dispatcher) {
                notificationInteractor.subscribeToTopic("main", token)
                subscribeToCityTopicIfPresent(token)
                sendUpToDateToken(token)
            }
        }
    }

    private suspend fun subscribeToCityTopicIfPresent(fcmDeviceToken: String) {
        val profile = profileInteractor.getProfile(false).getOrNull()
        if (profile != null && profile.city.isNotEmpty()) {
            notificationInteractor.subscribeToTopic(profile.city, fcmDeviceToken)
        }
    }

    private suspend fun sendUpToDateToken(fcmDeviceToken: String) {
        notificationInteractor.updateToken(fcmDeviceToken)
    }

    private fun checkUpdateAndDetermineNavRoute() {
        viewModelScope.launch(dispatcher) {
            val appStatus = splashInteractor.fetchAppStatus().getOrNull()
            val onboardingShown = onboardingInteractor.isIntroScreenShown()

            setEffect {
                when {
                    (appStatus?.technicalWorks == true) -> SplashContract.Effect.NavigateToTechWorks
                    !onboardingShown -> {
                        setDefaultCity()
                        SplashContract.Effect.NavigateToOnboarding
                    }
                    else -> SplashContract.Effect.NavigateToMain
                }
            }
        }
    }

    private fun setDefaultCity() {
        viewModelScope.launch(dispatcher) {
            headerInteractor.getCities().fold(
                onSuccess = { citiesMap ->
                    val defaultCity = citiesMap.values.first().first().name
                    appSettingsInteractor.setCity(defaultCity)
                },
                onFailure = {}
            )
        }
    }

}