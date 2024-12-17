package com.evothings.data.feature.auth.dto.response.token

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(
    @SerializedName("access") override val token: String,
    @SerializedName("expire_date") override val expireDate: String
) : TokenResponse
