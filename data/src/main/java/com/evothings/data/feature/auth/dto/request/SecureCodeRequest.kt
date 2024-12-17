package com.evothings.data.feature.auth.dto.request

data class SecureCodeRequest(
    val code: String,
    val phone: String
)
