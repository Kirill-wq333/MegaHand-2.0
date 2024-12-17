package com.evothings.data.feature.checkout.dto.response

import com.evothings.data.feature.product.dto.request.ProductId
import com.google.gson.annotations.SerializedName

data class CheckoutData(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val email: String,
    val phone: String,
    val source: String?,
    val comment: String,
    val city: String,
    val address: String?,
    @SerializedName("save_in_profile") val saveInProfile: Boolean,
    @SerializedName("cdek_order") val cdekOrder: CdekOrder?,
    @SerializedName("bonus_amount") val bonusAmount: String,
    @SerializedName("bonuses_count") val cashbackPointsReward: Number?,
    @SerializedName("total_base_price") val basePrice: Number?,
    @SerializedName("total_discount") val discount: Number?,
    @SerializedName("total_price") val price: Number?,
    val items: Array<ProductId>
)
