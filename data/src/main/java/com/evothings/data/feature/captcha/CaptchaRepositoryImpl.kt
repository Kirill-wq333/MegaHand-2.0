package com.evothings.data.feature.captcha

import com.evothings.data.feature.captcha.datasource.CaptchaNetworkClient
import com.evothings.data.feature.captcha.dto.CaptchaToken
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.captcha.repository.CaptchaRepository

class CaptchaRepositoryImpl(
    private val networkClient: CaptchaNetworkClient
) : CaptchaRepository {

    override suspend fun verifyCaptcha(token: String) {
        networkClient.verifyCaptcha(body = CaptchaToken(token)).awaitResult()
    }

}