package com.evothings.mhand.presentation.feature.coupon.viewmodel

import com.evothings.domain.feature.coupon.model.CouponForm
import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object CouponContract {

    sealed interface Event : ViewEvent {
        data class SendForm(val form: CouponForm) : Event
    }

    sealed interface State : ViewState {
        data object Idle : State
    }

    sealed interface Effect : ViewEffect {
        data class ShowToast(val message: String) : Effect
        data class OpenConfirmationScreen(val phone: String) : Effect
    }

}