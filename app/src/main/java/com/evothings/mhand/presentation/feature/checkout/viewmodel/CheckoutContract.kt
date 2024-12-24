package com.evothings.mhand.presentation.feature.checkout.viewmodel

import com.evothings.domain.feature.checkout.model.CheckoutResult
import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object CheckoutContract {

    sealed interface Event : ViewEvent {
        data class LoadCheckoutInfo(val orderId: String) : Event
        data class Refresh(val orderId: String) : Event
        data class Checkout(val orderId: String, val result: CheckoutResult) : Event
        data class LoadPickupPoints(val city: String) : Event
        data class HandlePaymentResult(val success: Boolean) : Event
        data object UpdateAddressesList : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
        data object ServerError : State
    }

    sealed interface Effect : ViewEffect {
        data class OpenPaymentActivity(val paymentLink: String) : Effect
        data object OpenProfileScreen : Effect
        data class ShowToast(val message: String) : Effect
    }

}