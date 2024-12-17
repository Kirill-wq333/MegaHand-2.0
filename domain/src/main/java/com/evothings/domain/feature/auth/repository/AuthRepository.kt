package com.evothings.domain.feature.auth.repository

interface AuthRepository {
    suspend fun isLoggedIn(): Boolean
    suspend fun requestCode(phone: String, refCode: String = ""): Result<Boolean>
    suspend fun resendCode(phone: String): Result<Unit>
    suspend fun requestSmsCode(phone: String): Result<Unit>
    suspend fun checkCode(phone: String, code: String): Result<Unit>
    suspend fun setSecureCode(code: String, phone: String): Result<Unit>
    suspend fun loginBySecureCode(code: String, phone: String = ""): Boolean
}