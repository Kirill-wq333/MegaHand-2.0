package com.evothings.data.feature.auth.dto.mapper

import com.evothings.data.feature.auth.dto.request.SecureCodeRequest

internal fun getSecureCodeRequest(code: String, phone: String): SecureCodeRequest {
    return SecureCodeRequest(code = code, phone = phone)
}