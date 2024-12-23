package com.evothings.mhand.presentation.feature.card.viewmodel

import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object CardContract {

    sealed interface Event : ViewEvent {
        data class LoadData(val offlineMode: Boolean) : Event
        data object ReloadPage : Event
        data object FinishOnboarding : Event
        data class ChangeFilter(val filterIndex: Int) : Event
        data object NotifyLoyalitySystemAppear : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
        data object LoyalityNotAvailable : State
        data object UserIsNotAuthorized : State
        data object OnboardingActive : State
        data object NetworkError : State
    }

    sealed interface Effect : ViewEffect {
        data object NavigateToProfile : Effect
    }

}