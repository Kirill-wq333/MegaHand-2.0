package com.evothings.mhand.presentation.feature.payment.launcher

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
internal inline fun rememberPaymentActivityLauncher(
    crossinline onPaymentFinished: (success: Boolean) -> Unit,
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult()
) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
        onPaymentFinished(true)
    } else if (result.resultCode == Activity.RESULT_CANCELED) {
        onPaymentFinished(false)
    }
}