package com.evothings.mhand.presentation.feature.shared.header.view

import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object HeaderContract {

    sealed interface Event : ViewEvent {
        data class ChooseCity(val city: String) : Event
        data object GetNotifications : Event
        data object GetCardBalance : Event
        data object ToggleDevMode : Event
        data object DeleteAllNotifications : Event
        data class DeleteNotification(val id: Int) : Event
        data object ReadAllNotifications : Event
    }

    sealed interface State : ViewState {
        data object Idle : State
    }

    sealed interface Effect : ViewEffect {
        data class ShowToast(val message: String) : Effect
    }

}