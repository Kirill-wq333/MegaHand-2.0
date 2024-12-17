package com.evothings.domain.feature.captcha

import com.evothings.domain.feature.captcha.repository.CaptchaRepository

class CaptchaInteractor(private val repository: CaptchaRepository) {

    suspend fun verifyCaptcha(token: String) =
        repository.verifyCaptcha(token)

}