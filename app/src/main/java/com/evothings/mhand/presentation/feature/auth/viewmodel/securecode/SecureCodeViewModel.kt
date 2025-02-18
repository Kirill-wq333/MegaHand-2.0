package com.evothings.mhand.presentation.feature.auth.viewmodel.securecode

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.settings.interactor.AppSettingsInteractor
import com.evothings.domain.feature.auth.interactor.AuthInteractor
import com.evothings.domain.feature.card.interactor.CardInteractor
import com.evothings.domain.feature.profile.interactor.ProfileInteractor
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.evothings.mhand.presentation.feature.snackbar.host.SnackbarItemHost
import com.evothings.mhand.presentation.feature.snackbar.model.SnackbarItem
import com.evothings.mhand.presentation.utils.sdkutil.ageFromDate
import com.my.tracker.MyTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecureCodeViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val profileInteractor: ProfileInteractor,
    private val cardInteractor: CardInteractor,
    private val appSettingsInteractor: AppSettingsInteractor,
    private val snackbarItemHost: SnackbarItemHost,
//    private val sharedPreferences: SharedPreferences
) : BaseViewModel<SecureCodeContract.Event, SecureCodeContract.State, SecureCodeContract.Effect>() {

    private val _errorState = MutableStateFlow(false)
    val errorState = _errorState.asStateFlow()

    private val _lockTimer = MutableStateFlow(0)
    val lockTimer = _lockTimer.asStateFlow()

    private val _isLocked: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLocked = _isLocked.asStateFlow()

    private val failAttemptsCount = MutableStateFlow(0)

    override fun setInitialState(): SecureCodeContract.State = SecureCodeContract.State.Initial

    override fun handleEvent(event: SecureCodeContract.Event) = when(event) {
        is SecureCodeContract.Event.SetSecureCode -> setCode(event.code, event.phone)
        is SecureCodeContract.Event.CheckSecureCode -> confirmSecureCode(event.code, event.phone)
        is SecureCodeContract.Event.SendConfirmCodeToReset -> sendConfirmCodeToReset(event.phone)
        is SecureCodeContract.Event.DisableConfirmErrorState -> disableErrorState()
        is SecureCodeContract.Event.SetLockTimer -> watchFailAttemptsCount()
    }

    init {
        watchFailAttemptsCount()
        watchLockTimer()
    }

    private fun watchFailAttemptsCount() {
        viewModelScope.launch(dispatcher) {
            failAttemptsCount.collect { attemptsCount ->
                if (attemptsCount == 3) {
                    _lockTimer.emit(300)
                    _isLocked.emit(true)
                    showLockSnackbar()
                }
            }
        }
    }

    private fun showLockSnackbar() {
        snackbarItemHost.setSnackbar(
            SnackbarItem(
                title = "Ты ввел неверный код 3 раза подряд",
                subtitle = "Вход был временно заблокирован"
            )
        )
    }

    private fun watchLockTimer() {
        viewModelScope.launch(dispatcher) {
            lockTimer.collect { remained ->
                if (remained > 0) {
                    delay(1000)
                    _lockTimer.update { remained - 1 }
                } else {
                    _isLocked.emit(false)
                    failAttemptsCount.emit(0)
                }
            }
        }
    }

    private fun disableErrorState() = _errorState.update { false }

    private fun sendConfirmCodeToReset(phone: String) {
        viewModelScope.launch(dispatcher) {
            authInteractor.resendCode(phone)
        }
    }

    private fun setCode(code: String, phone: String) {
        viewModelScope.launch(dispatcher) {
            authInteractor.sendSecureCode(code, phone)
                .fold(
                    onSuccess = {
                        profileInteractor.getProfile(true).fold(
                            onSuccess = { profile ->
                                appSettingsInteractor.setCity(profile.city)
                                setProfileInfoToTracker(profile)
                            },
                            onFailure = {}
                        )
                        setEffect { SecureCodeContract.Effect.NavigateToProfile }
                    },
                    onFailure = {
                        setEffect {
                            SecureCodeContract.Effect.ShowErrorToast("Не удалось отправить код")
                        }
                    }
                )
        }
    }

    private fun confirmSecureCode(code: String, phone: String = "") {
        viewModelScope.launch(dispatcher) {
            val isCorrect = authInteractor.confirmSecureCode(code, phone)
            if (isCorrect) {
                updateLocalCity()
                setEffect { SecureCodeContract.Effect.NavigateToProfile }
            } else {
                _errorState.emit(true)
                failAttemptsCount.update { it + 1 }
            }
        }
    }

    private suspend fun updateLocalCity() {
        profileInteractor.getProfile(true).fold(
            onSuccess = { profile ->
                val city = profile.city
                appSettingsInteractor.setCity(city)
                getCardAndUpdateWidget(city)
            },
            onFailure = {}
        )
    }

    private suspend fun getCardAndUpdateWidget(city: String) {
        cardInteractor.getCardInfo(forceOnline = true, offlineMode = false, city = city)
        setEffect { SecureCodeContract.Effect.UpdateWidgetData }
    }

    private fun setProfileInfoToTracker(profile: Profile) {
        MyTracker.trackLoginEvent(profile.phoneNumber, "")
        runCatching {
            MyTracker.getTrackerParams()
                .setCustomUserId(profile.phoneNumber)
                .setPhone(profile.phoneNumber)
                .setCustomParam("birthday", profile.birthday)
                .setCustomParam("city", profile.city)
                .setCustomParam("cashback", profile.cashback.toString())
                .setAge(ageFromDate(profile.birthday))
        }.getOrElse {}
    }

}