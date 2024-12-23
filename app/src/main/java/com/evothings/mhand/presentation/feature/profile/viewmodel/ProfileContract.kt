package com.evothings.mhand.presentation.feature.profile.viewmodel

import com.evothings.domain.feature.profile.model.Profile
import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object ProfileContract {

    sealed interface Event : ViewEvent {

        data object LoadData : Event
        data object Refresh : Event

        data class SaveProfileChanges(val profile: Profile) : Event
        data class ConfirmPhone(val newPhone: String) : Event

        data class SearchOrders(val query: String) : Event
        data class SearchByDate(val dateQuery: String) : Event
        data class HandleOrderPaymentStatus(val status: Boolean) : Event

        data object DeleteAccount : Event
        data object Logout : Event

        data object ToggleContentState : Event
        data object FinishOnboarding : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object UserData : State
        data object EmptyOrdersHistory : State
        data object OrdersHistory : State
        data object ServerError : State
        data object AuthorizationTechnicalWorks : State
        data object OnboardingActive : State
        data object NecessaryFieldsUnfilled : State
    }

    sealed interface Effect : ViewEffect {
        data class NavigateToCode(val newPhone: String) : Effect
        data object NavigateToAuth : Effect
        data object UpdateWidget : Effect
        data class ShowToast(val message: String) : Effect
    }

}