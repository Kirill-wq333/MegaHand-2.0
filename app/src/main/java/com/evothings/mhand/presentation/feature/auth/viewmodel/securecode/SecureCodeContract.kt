package com.evothings.mhand.presentation.feature.auth.viewmodel.securecode

import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object SecureCodeContract {

    sealed interface Event : ViewEvent {
        data class SetSecureCode(val code: String, val phone: String = "") : Event
        data class CheckSecureCode(val code: String, val phone: String = "") : Event
        data class SendConfirmCodeToReset(val phone: String) : Event
        data object DisableConfirmErrorState : Event
        data class SetLockTimer(val timer: Int) : Event
    }

    sealed interface State : ViewState {
        data object Initial : State
    }

    sealed interface Effect : ViewEffect {
        data class ShowErrorToast(val message: String) : Effect
        data object NavigateToProfile : Effect
        data object UpdateWidgetData : Effect
        data class NavigateToConfirmCode(val phone: String) : Effect
    }

}