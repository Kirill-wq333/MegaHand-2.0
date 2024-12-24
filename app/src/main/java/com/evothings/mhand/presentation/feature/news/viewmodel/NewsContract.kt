package com.evothings.mhand.presentation.feature.news.viewmodel

import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object NewsContract {

    sealed interface Event : ViewEvent {
        data object LoadData : Event
        data object Reload : Event
        data class SelectCategory(val index: Int) : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
        data object ServerError : State
    }

}