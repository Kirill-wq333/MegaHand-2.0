package com.evothings.domain.feature.checkout.interactor

import com.evothings.domain.feature.checkout.model.CheckoutResult
import com.evothings.domain.feature.checkout.model.DeliveryOption
import com.evothings.domain.feature.checkout.repository.CheckoutRepository

class CheckoutInteractor(private val repository: CheckoutRepository) {

    suspend fun getCheckoutInfo(id: String) = repository.getCheckoutInfo(id)

    suspend fun getDeliveryCost(id: String, city: String, option: DeliveryOption) =
        repository.getDeliveryCost(id, city, option)

    suspend fun getPointsDiscountAmount(
        orderId: String,
        deliveryCost: Double,
        pointsAmount: Int
    ) = repository.getPointsDiscount(orderId, deliveryCost, pointsAmount)

    suspend fun checkout(id: String, checkoutResult: CheckoutResult) =
        repository.checkout(id, checkoutResult)

    suspend fun getCdekPickupPoints(city: String) = repository.getCdekPoints(city)

}