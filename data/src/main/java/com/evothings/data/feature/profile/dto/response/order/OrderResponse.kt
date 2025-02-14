package com.evothings.data.feature.profile.dto.response.order

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    val id: String,
    @SerializedName("order_number")
    val orderId: String,
    val status: String,
    @SerializedName("status_display") val displayStatus: String,
    @SerializedName("created_at") val creationDate: String,
    val cost: Number,
    @SerializedName("payment_url")
    val paymentLink: String?,
    @SerializedName("track_number")
    val track: List<String>,
    val items: Array<OrderItem>
)
