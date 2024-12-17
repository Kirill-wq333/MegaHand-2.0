package com.evothings.data.feature.profile.dto.response.referals

import com.google.gson.annotations.SerializedName

data class ReferralDto(
    @SerializedName("full_name") val fullName: String,
    @SerializedName("cashback_level") val cashback: Int,
    @SerializedName("created_at") val createdAt: String
)
