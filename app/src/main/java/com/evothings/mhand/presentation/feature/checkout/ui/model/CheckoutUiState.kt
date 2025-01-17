package com.evothings.mhand.presentation.feature.checkout.ui.model

import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.checkout.model.PaymentMethod
import com.evothings.domain.feature.checkout.model.PickupPoint
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.feature.product.model.Product

data class CheckoutUiState(
    val profile: Profile = Profile(),
    val addresses: List<Address> = listOf(),
    val pickupCity: String = "",
    val pickupPoints: List<PickupPoint> = listOf(),
    val paymentMethods: List<PaymentMethod> = listOf(),
    val isLoyalityAvailable: Boolean = false,
    val cardBalance: Int = 0,
    val availableBalance: Double = 0.0,
    val cashback: Int = 0,
    val orderItems: List<Product> = listOf(),
    val total: Double = 0.0,
    val discount: Double = 0.0,
    val cashbackPoints: Double = 0.0,
    val summary: Double = 0.0
)
