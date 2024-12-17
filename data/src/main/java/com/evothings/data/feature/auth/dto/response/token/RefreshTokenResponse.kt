package com.evothings.data.feature.auth.dto.response.token

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("refresh") override val token: String,
    @SerializedName("refresh_expire_date") override val expireDate: String
) : TokenResponse
