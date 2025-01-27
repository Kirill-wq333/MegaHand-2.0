package com.evothings.domain.feature.checkout.model

import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.feature.profile.model.Profile


data class CheckoutInfo(
    val profile: Profile = Profile(),
    val addresses: List<Address> = listOf(),
    val cardIsAvailable: Boolean = false,
    val cardBalance: Int = 0,
    val availableBalance: Double = 0.0,
    val orderItems: List<Product> = listOf(),
    val cashbackPoints: Double,
    val basePrice: Double,
    val discount: Double,
    val price: Double,
)