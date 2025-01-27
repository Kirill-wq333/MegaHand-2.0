package com.evothings.data.feature.checkout.dto.response

import com.google.gson.annotations.SerializedName

data class DeliveryPriceResponse(
    @SerializedName("delivery_price") val deliveryPrice: Number,
    @SerializedName("total_price") val totalPrice: Number
)
