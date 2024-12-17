package com.evothings.domain.feature.captcha.repository

interface CaptchaRepository {
    suspend fun verifyCaptcha(token: String)
}