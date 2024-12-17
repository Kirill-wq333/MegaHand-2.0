package com.evothings.mhand.presentation.feature.shared.screen.chooseCity.viewmodel

import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object ChooseCityContract {

    sealed interface Event : ViewEvent {
        data class LoadCities(val showChosen: Boolean) : Event
        data class ChooseCity(val newCity: String) : Event
        data class SearchCity(val query: String) : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
        data object NetworkError : State
    }

}