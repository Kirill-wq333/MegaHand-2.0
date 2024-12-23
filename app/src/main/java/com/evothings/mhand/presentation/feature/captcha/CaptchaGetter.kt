package com.evothings.mhand.presentation.feature.captcha

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
internal inline fun rememberCaptchaActivityLauncher(
    crossinline onResult: (success: Boolean) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> =
    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when(result.resultCode) {
            Activity.RESULT_OK -> {
                onResult(true)
            }
            Activity.RESULT_CANCELED -> {
                onResult(false)
            }
        }
    }