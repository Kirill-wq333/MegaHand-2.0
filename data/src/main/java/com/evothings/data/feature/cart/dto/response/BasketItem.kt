package com.evothings.data.feature.cart.dto.response

import com.evothings.data.feature.product.dto.response.ProductResponse
import com.google.gson.annotations.SerializedName

data class BasketItem(
    @SerializedName("product_id")
    val productId: Int,
    val quantity: Int,
    @SerializedName("product_info")
    val product: ProductResponse
)
