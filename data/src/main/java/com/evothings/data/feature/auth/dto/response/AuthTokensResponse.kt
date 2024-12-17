package com.evothings.data.feature.auth.dto.response

import com.google.gson.annotations.SerializedName

data class AuthTokensResponse(
    @SerializedName("access_token") val access: String,
    @SerializedName("refresh_token") val refresh: String,
    @SerializedName("expire_date") val accessExpirationDate: String,
    @SerializedName("refresh_expire_date") val refreshExpireDate: String
)
