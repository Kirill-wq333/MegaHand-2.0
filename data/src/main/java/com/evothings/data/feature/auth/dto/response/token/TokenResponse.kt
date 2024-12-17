package com.evothings.data.feature.auth.dto.response.token

sealed interface TokenResponse {
    val token: String
    val expireDate: String
}