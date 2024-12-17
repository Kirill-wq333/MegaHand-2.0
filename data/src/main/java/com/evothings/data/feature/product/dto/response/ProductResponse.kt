package com.evothings.data.feature.product.dto.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val id: Int,
    val name: String,
    val description: String,
    val photos: Array<ProductPhoto>,
    @SerializedName("main_photo") val mainPhoto: String,
    @SerializedName("base_price") val oldPrice: Number,
    val discount: Number,
    @SerializedName("is_discount_percent")
    val isPercentDiscount: Boolean,
    val price: Number,
    @SerializedName("is_new") val isNew: Boolean,
    val brand: Int?,
    val size: String,
    val color: String,
    val quality: String,
    @SerializedName("bonuses_count") val bonusesCount: Number?,
    @SerializedName("in_stock") val inStock: Boolean,
    @SerializedName("characteristics") val properties: Array<PropertyResponse>,
    @SerializedName("in_basket") val isInCart: Boolean,
    @SerializedName("in_wishlist") val isFavourite: Boolean
)
