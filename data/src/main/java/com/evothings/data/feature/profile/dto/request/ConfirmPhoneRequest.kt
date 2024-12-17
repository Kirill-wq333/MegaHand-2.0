package com.evothings.data.feature.profile.dto.request

data class ConfirmPhoneRequest(
    val code: Int,
    val phone: String,
)
