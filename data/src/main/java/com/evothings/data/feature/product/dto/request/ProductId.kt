package com.evothings.data.feature.product.dto.request

import com.google.gson.annotations.SerializedName

data class ProductId(
    @SerializedName("product_id")
    val productId: Int
)
