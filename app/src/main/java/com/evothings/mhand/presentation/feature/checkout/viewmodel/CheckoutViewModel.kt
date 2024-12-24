package com.evothings.mhand.presentation.feature.checkout.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.address.interactor.AddressInteractor
import com.evothings.domain.feature.checkout.interactor.CheckoutInteractor
import com.evothings.domain.feature.checkout.model.CheckoutResult
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.evothings.mhand.presentation.feature.checkout.ui.model.CheckoutUiState
import com.evothings.mhand.presentation.feature.snackbar.host.SnackbarItemHost
import com.evothings.mhand.presentation.feature.snackbar.model.SnackbarItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val checkoutInteractor: CheckoutInteractor,
    private val addressInteractor: AddressInteractor,
    private val snackbarItemHost: SnackbarItemHost
) : BaseViewModel<CheckoutContract.Event, CheckoutContract.State, CheckoutContract.Effect>() {

    private val _uiState: MutableStateFlow<CheckoutUiState> = MutableStateFlow(CheckoutUiState())
    val uiState = _uiState.asStateFlow()

    override fun setInitialState(): CheckoutContract.State = CheckoutContract.State.Loading

    override fun handleEvent(event: CheckoutContract.Event) = when(event) {
        is CheckoutContract.Event.LoadCheckoutInfo -> loadCheckoutInfo(event.orderId)
        is CheckoutContract.Event.LoadPickupPoints -> loadPickupPoints(event.city)
        is CheckoutContract.Event.Checkout -> checkout(event.orderId, event.result)
        is CheckoutContract.Event.HandlePaymentResult -> handlePaymentResult(event.success)
        is CheckoutContract.Event.UpdateAddressesList -> updateAddressesList()
        is CheckoutContract.Event.Refresh -> refresh(event.orderId)
    }

    private fun loadCheckoutInfo(id: String) {
        viewModelScope.launch(dispatcher) {
            checkoutInteractor.getCheckoutInfo(id).fold(
                onSuccess = { checkoutInfo ->
                    _uiState.update {
                        it.copy(
                            profile = checkoutInfo.profile,
                            addresses = checkoutInfo.addresses,
                            cardBalance = checkoutInfo.cardBalance,
                            isLoyalityAvailable = checkoutInfo.cardIsAvailable,
                            availableBalance = checkoutInfo.availableBalance,
                            cashback = checkoutInfo.profile.cashback,
                            orderItems = checkoutInfo.orderItems,
                            total = checkoutInfo.basePrice,
                            discount = checkoutInfo.discount,
                            cashbackPoints = checkoutInfo.cashbackPoints,
                            summary = checkoutInfo.price
                        )
                    }
                    loadPickupPoints(checkoutInfo.profile.city)
                    setState(CheckoutContract.State.Loaded)
                },
                onFailure = {
                    setEffect { CheckoutContract.Effect.ShowToast("Error: ${it.message}") }
                    setState(CheckoutContract.State.ServerError)
                }
            )
        }
    }

    private fun updateAddressesList() {
        viewModelScope.launch(dispatcher) {
            addressInteractor.getUserAddresses()
                .onSuccess { addresses ->
                    _uiState.update { it.copy(addresses = addresses) }
                }
        }
    }

    private fun loadPickupPoints(newCity: String) {
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(pickupCity = newCity) }
            checkoutInteractor.getCdekPickupPoints(newCity)
                .onSuccess { points ->
                    _uiState.update {
                        it.copy(
                            pickupPoints = points
                        )
                    }
                }
        }
    }

    private fun refresh(id: String) {
        setState(CheckoutContract.State.Loading)
        loadCheckoutInfo(id)
    }

    private fun checkout(id: String, result: CheckoutResult) {
        viewModelScope.launch(dispatcher) {
            setState(CheckoutContract.State.Loading)
            checkoutInteractor.updateCheckoutInfo(id, result).fold(
                onSuccess = { paymentLink ->
                    setEffect { CheckoutContract.Effect.OpenPaymentActivity(paymentLink) }
                },
                onFailure = {
                    setState(CheckoutContract.State.Loaded)
                    setEffect { CheckoutContract.Effect.ShowToast("Error: ${it.message}") }
                }
            )
        }
    }

    private fun handlePaymentResult(status: Boolean) {
        val snackbar = if (status) {
            SnackbarItem(
                title = "Заказ успешно оформлен!",
                subtitle = "Его статус будет отображаться в личном кабинете"
            )
        } else {
            SnackbarItem(
                title = "Заказ не был оплачен",
                subtitle = "Попробуйте оплатить заказ заново в личном кабинете"
            )
        }
        setState(CheckoutContract.State.Loaded)
        snackbarItemHost.setSnackbar(snackbar)
        setEffect { CheckoutContract.Effect.OpenProfileScreen }
    }

}