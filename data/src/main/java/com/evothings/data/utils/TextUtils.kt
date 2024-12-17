package com.evothings.data.utils

fun String.formatPhone(): String {
    val cleanNumber = this.replace(Regex("[^0-9]"), "")

    return runCatching {
        if (cleanNumber.length == 11 && cleanNumber.startsWith('7')) {
            val formattedNumber = "+7 ${cleanNumber.substring(1, 4)} ${cleanNumber.substring(4, 7)}-${cleanNumber.substring(7, 9)}-${cleanNumber.substring(9)}"
            formattedNumber
        } else {
            "Invalid phone number format"
        }
    }.getOrElse { this }
}