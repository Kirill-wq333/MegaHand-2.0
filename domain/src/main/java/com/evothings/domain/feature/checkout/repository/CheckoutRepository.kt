package com.evothings.domain.feature.checkout.repository

import com.evothings.domain.feature.checkout.model.CheckoutInfo
import com.evothings.domain.feature.checkout.model.CheckoutResult
import com.evothings.domain.feature.checkout.model.PickupPoint

interface CheckoutRepository {

    suspend fun getCheckoutInfo(id: String): Result<CheckoutInfo>

    suspend fun updateCheckoutInfo(id: String, checkoutResult: CheckoutResult): Result<String>

    suspend fun getCdekPoints(city: String): Result<List<PickupPoint>>

}