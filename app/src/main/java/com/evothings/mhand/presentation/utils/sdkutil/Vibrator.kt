package com.evothings.mhand.presentation.utils.sdkutil

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

object Vibrator {

    fun makeVibration(durationMillis: Long, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibrator = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibrator.vibrate(
                CombinedVibration.createParallel(
                    VibrationEffect.createOneShot(
                        durationMillis, VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            )
        } else {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    durationMillis, VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
    }

}