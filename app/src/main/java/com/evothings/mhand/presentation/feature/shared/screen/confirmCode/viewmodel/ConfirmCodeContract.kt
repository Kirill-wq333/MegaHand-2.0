package com.evothings.mhand.presentation.feature.shared.screen.confirmCode.viewmodel

import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState
import com.evothings.mhand.presentation.feature.shared.screen.confirmCode.viewmodel.model.ConfirmCodeUseCase

object ConfirmCodeContract {

    sealed interface Event : ViewEvent {
        data class ConfirmCode(
            val phone: String,
            val code: String,
            val type: ConfirmCodeUseCase
        ) : Event
        data object DisableErrorState : Event
        data class SendSMSCode(val phone: String) : Event
        data class SendInitialProfileCode(val phone: String) : Event
    }

    sealed interface State : ViewState {
        data object InitialState : State
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToSecureCode : Effect
        data object OpenProfileScreen : Effect
        data object CouponSuccess : Effect
        data class ShowToast(val message: String) : Effect

    }

}