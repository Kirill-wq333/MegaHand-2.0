package com.evothings.mhand.presentation.feature.auth.viewmodal

import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object AuthContract {

    sealed interface Event : ViewEvent {
        data class SendAuthData(val phone: String, val refCode: String) : Event
        data object ResetState : Event
    }

    sealed interface State : ViewState {
        data object Idle : State
        data object Loading : State
    }

    sealed interface Effect : ViewEffect {
        data class NavigateToCode(val phone: String) : Effect
        data class NavigateToSecureCode(val phone: String) : Effect
        data class ShowErrorToast(val message: String) : Effect

    }

}