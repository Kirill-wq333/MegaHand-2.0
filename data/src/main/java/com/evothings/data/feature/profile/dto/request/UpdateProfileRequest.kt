package com.evothings.data.feature.profile.dto.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val birthday: String?,
    val city: String?,
    val email: String?,
)