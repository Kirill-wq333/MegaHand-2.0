package com.evothings.data.feature.checkout.dto.response

import com.google.gson.annotations.SerializedName

data class AmountResponse(
    @SerializedName("amount")
    val availableBalance: Number
)
