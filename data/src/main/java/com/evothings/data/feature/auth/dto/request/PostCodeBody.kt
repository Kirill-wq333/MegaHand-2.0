package com.evothings.data.feature.auth.dto.request

data class PostCodeBody(
    val phone: String,
    val code: Int,
    val source: String = "APP"
)
