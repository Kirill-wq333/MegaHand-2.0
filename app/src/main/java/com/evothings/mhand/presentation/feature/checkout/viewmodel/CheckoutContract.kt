package com.evothings.mhand.presentation.feature.checkout.viewmodel

import com.evothings.domain.feature.address.model.Address
import com.evothings.mhand.core.viewmodel.*
import com.evothings.domain.feature.checkout.model.CheckoutResult
import com.evothings.domain.feature.checkout.model.DeliveryOption

object CheckoutContract {

    sealed interface Event : ViewEvent {
        data class LoadCheckoutInfo(val orderId: String) : Event
        data class Refresh(val orderId: String) : Event
        data class SaveAddress(val address: Address) : Event
        data class Checkout(val orderId: String, val result: CheckoutResult) : Event
        data class LoadPickupPoints(val city: String) : Event
        data class CalculateDeliveryCost(
            val id: String,
            val city: String,
            val deliveryOption: DeliveryOption
        ) : Event
        data class CalculatePointsDiscount(val id: String, val pointsAmount: Int) : Event
        data class HandlePaymentResult(val success: Boolean) : Event
        data object UpdateAddressesList : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
        data object ServerError : State
    }

    sealed interface Effect : ViewEffect {
        data class OpenPaymentActivity(val paymentLink: String) : Effect
        data object OpenProfileScreen : Effect
        data class ShowToast(val message: String) : Effect
    }

}