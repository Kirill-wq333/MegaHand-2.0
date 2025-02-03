package com.evothings.mhand.presentation.feature.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.auth.interactor.AuthInteractor
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.evothings.mhand.presentation.feature.snackbar.host.SnackbarItemHost
import com.evothings.mhand.presentation.feature.snackbar.model.SnackbarItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
) : BaseViewModel<AuthContract.Event, AuthContract.State, AuthContract.Effect>() {

    override fun setInitialState(): AuthContract.State = AuthContract.State.Idle

    override fun handleEvent(event: AuthContract.Event) = when(event) {
        is AuthContract.Event.SendAuthData -> sendAuthCode(event.phone, event.refCode)
        is AuthContract.Event.ResetState -> setState(AuthContract.State.Idle)
    }

    private fun sendAuthCode(phone: String, refCode: String) {
        viewModelScope.launch(dispatcher) {
            setState(AuthContract.State.Loading)
            authInteractor.requestCode(phone, refCode).fold(
                onSuccess = { createAccount ->
                    setEffect {
                        if (createAccount) {
                            AuthContract.Effect.NavigateToCode(phone)
                        } else {
                            AuthContract.Effect.NavigateToSecureCode(phone)
                        }
                    }
                },
                onFailure = {
                    setEffect {
                        AuthContract.Effect.ShowErrorToast("Ошибка при отправке кода")
                    }
                    setState(AuthContract.State.Idle)
                }
            )
        }
    }

}