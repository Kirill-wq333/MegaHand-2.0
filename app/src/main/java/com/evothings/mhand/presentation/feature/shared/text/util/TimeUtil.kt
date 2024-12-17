package com.evothings.mhand.presentation.feature.shared.text.util

fun Int.convertSecsToClock(): String {
    val minutes = this / 60
    val remainingSeconds = this % 60
    val formattedMinutes = String.format("%02d", minutes)
    val formattedSeconds = String.format("%02d", remainingSeconds)
    return "$formattedMinutes:$formattedSeconds"
}