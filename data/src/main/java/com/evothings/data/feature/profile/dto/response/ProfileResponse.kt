package com.evothings.data.feature.profile.dto.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    val phone: String,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    val email: String?,
    val city: String?,
    val birthday: String?,
    @SerializedName("cashback_level")
    val cashback: Int,
    @SerializedName("referral_code")
    val referralCode: String,
)
