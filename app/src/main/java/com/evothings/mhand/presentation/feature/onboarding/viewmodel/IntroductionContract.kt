package com.evothings.mhand.presentation.feature.onboarding.viewmodel

import com.evothings.mhand.core.viewmodel.*

object IntroductionContract {

    sealed interface Event : ViewEvent {
        data object DeclineOnboarding : Event
        data object Proceed : Event
    }

    sealed interface State : ViewState {
        data object Idle : State
    }

    sealed interface Effect : ViewEffect {
        data object OpenMainScreen : Effect
    }

}