package com.evothings.mhand.presentation.feature.shops.viewmodel

import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object ShopsContract {

    sealed interface Event : ViewEvent {
        data object LoadData : Event
        data object Refresh : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
        data object ServerError : State
    }

    sealed interface Effect : ViewEffect {
        data class ShowErrorToast(val message: String) : Effect
    }

}