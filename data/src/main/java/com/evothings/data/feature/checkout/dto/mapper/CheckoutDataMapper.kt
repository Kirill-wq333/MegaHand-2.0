package com.evothings.data.feature.checkout.dto.mapper

import com.evothings.data.feature.checkout.dto.response.CheckoutData
import com.evothings.domain.feature.checkout.model.CheckoutInfo
import com.evothings.domain.feature.checkout.model.CheckoutResult
import com.evothings.domain.feature.profile.model.Profile

internal fun CheckoutData.toCheckoutInfo(): CheckoutInfo {
    return CheckoutInfo(
        profile = Profile(),
        addresses = listOf(),
        basePrice = basePrice?.toDouble() ?: 0.0,
        discount = discount?.toDouble() ?: 0.0,
        cashbackPoints = cashbackPointsReward?.toDouble() ?: 0.0,
        price = price?.toDouble() ?: 0.0
    )
}

internal fun CheckoutResult.toCheckoutData(): CheckoutData {
    return CheckoutData(
        firstName = name,
        lastName = surname,
        email = email,
        phone = phone,
        comment = "",
        source = "APP",
        address = address.ifEmpty { null },
        saveInProfile = saveInProfile,
        bonusAmount = withdrawAmount.toString(),
        cdekOrder = pickupPoint.toCdekOrder(),
        city = "",
        basePrice = null,
        cashbackPointsReward = null,
        discount = null,
        items = arrayOf(),
        price = null
    )
}