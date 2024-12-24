package com.evothings.mhand.presentation.feature.product.viewmodel

import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object ProductContract {

    sealed interface Event : ViewEvent {
        data class LoadProduct(val id: Int) : Event
        data class AddToCart(val id: Int) : Event
        data class ToggleFavourite(val id: Int) : Event
        data class Reload(val id: Int) : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
        data object ServerError : State
    }

    sealed interface Effect : ViewEffect {
        data class ShowToast(val message: String) : Effect
    }

}