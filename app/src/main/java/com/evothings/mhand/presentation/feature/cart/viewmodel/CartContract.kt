package com.evothings.mhand.presentation.feature.cart.viewmodel

import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object CartContract {

    sealed interface Event : ViewEvent {
        data object LoadCart : Event
        data object Refresh : Event
        data class DeleteFromCart(val id: Int) : Event
        data class ToggleFavourite(val id: Int) : Event
        data class CalculateCheckout(val selection: List<Int>) : Event
        data class CreateOrder(val selection: List<Int>) : Event
        data object FinishOnboarding : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
        data object NoItems : State
        data object OnboardingActive : State
        data object ServerError : State
    }

    sealed interface Effect : ViewEffect {
        data class OpenCheckoutScreen(val orderId: String) : Effect
        data class ShowToast(val message: String) : Effect
    }

}