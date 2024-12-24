package com.evothings.mhand.presentation.feature.address.viewmodel.map

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.checkout.model.MapPoint
import com.evothings.domain.feature.settings.interactor.AppSettingsInteractor
import com.evothings.mhand.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressMapViewModel @Inject constructor(
    private val appSettingsInteractor: AppSettingsInteractor
) : BaseViewModel<AddressMapContract.Event, AddressMapContract.State, Nothing>() {

    private val _city = MutableStateFlow("")
    val city = _city.asStateFlow()

    private val _initialPoint: MutableStateFlow<MapPoint> = MutableStateFlow(MapPoint(0.0, 0.0))
    val initialPoint = _initialPoint.asStateFlow()

    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    override fun setInitialState(): AddressMapContract.State = AddressMapContract.State.InitialPointLoading

    override fun handleEvent(event: AddressMapContract.Event) = when(event) {
        is AddressMapContract.Event.SetCity -> _city.update { event.city }
        is AddressMapContract.Event.SetUserDefaultCity -> getUserDefaultCity()
        is AddressMapContract.Event.SetInitialPoint -> setInitialPoint(event.point)
        is AddressMapContract.Event.ConsumeGuessedAddress -> joinPartsToAddressString(event.guessedAddress)
    }

    private fun joinPartsToAddressString(guessedAddress: android.location.Address) {
        val joinedAddress = // Join address to format: 445039, Тольятти, Автостроителей, 56
            "${guessedAddress.postalCode}, ${guessedAddress.locality}, ${guessedAddress.thoroughfare}, ${guessedAddress.subThoroughfare}"
        _address.update { joinedAddress }
    }

    private fun getUserDefaultCity() {
        viewModelScope.launch(dispatcher) {
            val userCity = appSettingsInteractor.city.first()
            _city.update { userCity }
        }
    }

    private fun setInitialPoint(point: MapPoint) {
        _initialPoint.update { point }
        setState(AddressMapContract.State.Loaded)
    }

}