package com.evothings.domain.feature.checkout.repository

import com.evothings.domain.feature.checkout.model.CheckoutInfo
import com.evothings.domain.feature.checkout.model.CheckoutResult
import com.evothings.domain.feature.checkout.model.DeliveryOption
import com.evothings.domain.feature.checkout.model.DeliveryPrice
import com.evothings.domain.feature.checkout.model.PickupPoint
import com.evothings.domain.feature.checkout.model.PriceDetails

interface CheckoutRepository {

    suspend fun getCheckoutInfo(id: String): Result<CheckoutInfo>

    suspend fun getDeliveryCost(
        id: String,
        city: String,
        option: DeliveryOption
    ): Result<DeliveryPrice>

    suspend fun getPointsDiscount(
        id: String,
        deliveryCost: Double,
        pointsToWithdraw: Int
    ): Result<PriceDetails>

    suspend fun checkout(id: String, checkoutResult: CheckoutResult): Result<String>

    suspend fun getCdekPoints(city: String): Result<List<PickupPoint>>

}