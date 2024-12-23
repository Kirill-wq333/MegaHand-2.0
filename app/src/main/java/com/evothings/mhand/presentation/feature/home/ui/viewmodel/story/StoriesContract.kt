package com.evothings.mhand.presentation.feature.home.viewmodel.story

import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object StoriesContract {

    sealed class Event : ViewEvent {
        object LoadStories : Event()
    }

    sealed class State : ViewState {
        object Loading : State()
        object Loaded : State()
        object NetworkFailure : State()
    }

}