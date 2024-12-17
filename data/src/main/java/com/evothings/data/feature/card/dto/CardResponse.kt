package com.evothings.data.feature.card.dto

import com.google.gson.annotations.SerializedName

data class CardResponse(
    @SerializedName("qr_code") val qrCodeUrl: String,
    @SerializedName("active_balance") val balance: Number,
    @SerializedName("burnable_amount") val burnableAmount: Number?,
    @SerializedName("burnable_date") val burnDate: String?,
    @SerializedName("cashback_level") val cashbackLevel: Int,
    @SerializedName("is_max_cashback_level") val isMaxCashbackLevel: Boolean
)