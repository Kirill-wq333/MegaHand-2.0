package com.evothings.mhand.presentation.feature.captcha

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.evothings.mhand.presentation.feature.captcha.vm.CaptchaViewModel
import com.hcaptcha.sdk.HCaptcha
import dagger.hilt.android.AndroidEntryPoint

private const val SITE_KEY = "f249e271-9632-4174-b042-eb5db9e1138b"

@AndroidEntryPoint
class HCaptchaActivity : AppCompatActivity() {

    private val viewModel: CaptchaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hCaptchaClient = HCaptcha.getClient(this).setup(SITE_KEY)
        val phone = intent.extras?.getString("phone") ?: ""
        val intent = Intent()

        hCaptchaClient.verifyWithHCaptcha()
            .addOnSuccessListener {
                intent.putExtra("captchaToken", it.tokenResult)
                intent.putExtra("phone", phone)
                viewModel.verifyCaptcha(it.tokenResult)
                setResult(RESULT_OK, intent)
                finish()
            }
            .addOnFailureListener {
                setResult(RESULT_CANCELED)
                finish()
            }
    }

}