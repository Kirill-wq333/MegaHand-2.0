package com.evothings.domain.feature.shops.model

import com.evothings.domain.feature.checkout.model.MapPoint
import kotlinx.serialization.Serializable

@Serializable
data class Shop(
    val address: String,
    val shortAddress: String?,
    val workSchedule: String,
    val point: MapPoint,
    val phone: String,
    val email: String,
    val discountWeeks: List<DiscountDay>,
    val productAdditionDays: String
)

 