package com.evothings.mhand.presentation.feature.address.viewmodel.map

import com.evothings.domain.feature.checkout.model.MapPoint
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object AddressMapContract {

    sealed interface Event : ViewEvent {
        data class SetCity(val city: String) : Event
        data object SetUserDefaultCity : Event
        data class SetInitialPoint(val point: MapPoint) : Event
        data class ConsumeGuessedAddress(val guessedAddress: android.location.Address) : Event
    }

    sealed interface State : ViewState {
        data object InitialPointLoading : State
        data object Loaded : State
    }


}