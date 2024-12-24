package com.evothings.mhand.presentation.feature.splash.viewmodel

import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object SplashContract {

    sealed interface Event : ViewEvent {
        data object DetermineNavRoute : Event
    }

    sealed interface State : ViewState {
        data object Initial : State
        data object TechWorks: State

    }

    sealed interface Effect : ViewEffect {
        data object NavigateToOnboarding : Effect
        data object NavigateToTechWorks : Effect
        data object NavigateToMain : Effect
    }

}