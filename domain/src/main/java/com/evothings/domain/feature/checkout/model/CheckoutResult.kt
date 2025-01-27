package com.evothings.domain.feature.checkout.model

data class CheckoutResult(
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val saveInProfile: Boolean,
    val deliveryOption: DeliveryOption,
    val pickupPoint: PickupPoint,
    val address: String,
    val withdrawPoints: Boolean,
    val withdrawAmount: Int,
)



