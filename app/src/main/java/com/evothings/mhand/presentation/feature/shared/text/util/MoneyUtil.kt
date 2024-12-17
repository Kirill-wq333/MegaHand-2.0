package com.evothings.mhand.presentation.feature.shared.text.util

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs

fun Double.splitHundreds(separator: NumberSeparator = NumberSeparator.COMMA): String {
    val hasDecimalPart = !this.rem(1).equals(0.0)
    return if (hasDecimalPart) {
        this.toString().splitHundreds(separator)
    } else {
        this.toInt().splitHundreds(separator)
    }
}

fun Int.splitHundreds(separator: NumberSeparator = NumberSeparator.COMMA) =
    this.toString().splitHundreds(separator)

fun String.splitHundreds(separator: NumberSeparator = NumberSeparator.COMMA): String {
    if (this == "???" || this == "") return this
    val number = this.toDouble()
    if (number < 100) return this

    val absNumber = abs(number)
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
    val formattedNum = formatter.format(absNumber)

    return formattedNum.replaceFirst(',', separator.value)
}