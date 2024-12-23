package com.evothings.mhand.presentation.feature.captcha.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.captcha.CaptchaInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptchaViewModel @Inject constructor(
    private val captchaInteractor: CaptchaInteractor
) : ViewModel() {

    fun verifyCaptcha(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            captchaInteractor.verifyCaptcha(token)
        }
    }

}