package com.evothings.mhand.presentation.feature.home.viewmodel

import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState
import com.evothings.mhand.presentation.feature.splash.viewmodel.checkUpdate.CheckUpdateService

object HomeContract {

    sealed interface Event : ViewEvent {
        data object LoadData : Event
        data object Refresh : Event
        data object RefreshProductsList : Event
        data object EnableNoInternetConnectionState : Event
        data object FinishOnboarding : Event
        data class AddProductToCart(val id: Int) : Event
        data class ToggleFavouriteOnProduct(val id: Int) : Event
        data class SubmitUserSurvey(val chosen: Int) : Event
        data class CheckIsUpdateAvailable(val updateService: CheckUpdateService) : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
        data object NetworkError : State
        data object NoInternetConnection : State
        data object OnboardingActive : State
    }

}