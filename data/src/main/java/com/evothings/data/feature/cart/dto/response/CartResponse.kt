package com.evothings.data.feature.cart.dto.response

import com.google.gson.annotations.SerializedName

data class CartResponse(
    val products: Array<BasketItem>,
    @SerializedName("total_base_price") val basePrice: Number,
    @SerializedName("total_discount") val discount: Number,
    @SerializedName("total_price") val summary: Number,
    @SerializedName("product_count") val productsCount: Int,
    @SerializedName("bonuses_count") val cashbackPoints: Number,
)
