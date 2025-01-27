package com.evothings.data.feature.checkout.dto.response

import com.google.gson.annotations.SerializedName

data class PriceDetailsResponse(
    @SerializedName("bonus_amount") val pointsDiscount: Number?,
    @SerializedName("total_base_price") val basePrice: Number,
    @SerializedName("total_discount") val discount: Number,
    @SerializedName("bonuses_count") val pointsCashback: Number,
    @SerializedName("total_price") val totalPrice: Number
)
