package com.evothings.data.feature.checkout.dto.response

import com.google.gson.annotations.SerializedName

data class PaymentLinkResponse(
    @SerializedName("redirect_url")
    val link: String
)
