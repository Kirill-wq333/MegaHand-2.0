package com.evothings.data.feature.checkout.dto.response

import com.google.gson.annotations.SerializedName

data class CdekOrder(
    @SerializedName("shipment_point") val pickupPointCode: String,
    @SerializedName("delivery_type") val deliveryTariff: Int
)
