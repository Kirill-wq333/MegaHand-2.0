package com.evothings.data.feature.checkout.dto.request

import com.google.gson.annotations.SerializedName

data class DeliveryPriceRequest(
    @SerializedName("address") val city: String,
    @SerializedName("tariff_code") val deliveryOptionCode: Int
)
