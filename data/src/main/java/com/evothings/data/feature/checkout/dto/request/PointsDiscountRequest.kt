package com.evothings.data.feature.checkout.dto.request

import com.google.gson.annotations.SerializedName

data class PointsDiscountRequest(
    @SerializedName("delivery_price") val deliveryCost: Double,
    @SerializedName("bonus_amount") val pointsToWithdraw: Int
)
