package com.evothings.domain.feature.shops.model

import com.evothings.domain.feature.shops.model.enumeration.DiscountType
import kotlinx.serialization.Serializable

@Serializable
data class DiscountDay(
    val dayOfMonth: Int = 0,
    val dayOfWeek: String = "",
    val discount: String = "0",
    val isActive: Boolean = false,
    val isToday: Boolean = false,
    val hasAddition: Boolean = false,
    val type: DiscountType = DiscountType.BY_PERCENTS,
)