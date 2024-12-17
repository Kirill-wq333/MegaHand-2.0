package com.evothings.domain.feature.auth.interactor

import com.evothings.domain.feature.auth.repository.AuthRepository

class AuthInteractor(private val authRepository: AuthRepository) {

    suspend fun checkIsAuthorized(): Boolean =
        authRepository.isLoggedIn()

    suspend fun requestCode(phone: String, refCode: String): Result<Boolean> =
        authRepository.requestCode(phone, refCode)

    suspend fun sendCode(phone: String, code: String): Result<Unit> =
        authRepository.checkCode(phone, code)

    suspend fun sendSecureCode(code: String, phone: String) =
        authRepository.setSecureCode(code, phone)

    suspend fun confirmSecureCode(code: String, phone: String = "") =
        authRepository.loginBySecureCode(code, phone)

    suspend fun resendCode(phone: String) =
        authRepository.resendCode(phone)

    suspend fun requestSMS(phone: String) =
        authRepository.requestSmsCode(phone)

}