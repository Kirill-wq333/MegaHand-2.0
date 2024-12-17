package com.evothings.data.feature.auth.dto.request

import com.google.gson.annotations.SerializedName

data class RequestCodeBody(
    val phone: String,
    @SerializedName("referral_code")
    val refCode: String,
    val source: String = "APP"
)