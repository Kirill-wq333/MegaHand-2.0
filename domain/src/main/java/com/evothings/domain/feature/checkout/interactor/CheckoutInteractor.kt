package com.evothings.domain.feature.checkout.interactor

import com.evothings.domain.feature.checkout.model.CheckoutResult
import com.evothings.domain.feature.checkout.repository.CheckoutRepository

class CheckoutInteractor(private val repository: CheckoutRepository) {

    suspend fun getCheckoutInfo(id: String) = repository.getCheckoutInfo(id)

    suspend fun updateCheckoutInfo(id: String, checkoutResult: CheckoutResult) =
        repository.updateCheckoutInfo(id, checkoutResult)

    suspend fun getCdekPickupPoints(city: String) = repository.getCdekPoints(city)

}