package com.evothings.data.feature.cart.dto.response

import com.google.gson.annotations.SerializedName

data class OrderIdResponse(
    @SerializedName("order_id")
    val orderId: String
)
