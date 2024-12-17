package com.evothings.data.feature.profile.dto.response.order

import com.google.gson.annotations.SerializedName

data class OrderItem(
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("main_photo")
    val photo: String?
)
