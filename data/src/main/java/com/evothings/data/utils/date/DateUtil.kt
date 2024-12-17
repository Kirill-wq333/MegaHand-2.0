package com.evothings.data.utils.date

import com.evothings.domain.util.DateFormat.FULL_DATE
import com.evothings.domain.util.DateFormat.FULL_DATE_MICROSECONDS
import com.evothings.domain.util.DateFormat.WESTERN_DATE
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

internal fun String.daysSinceDate(): Int {
    val formatter = DateTimeFormatter.ofPattern(WESTERN_DATE)
    val date = formatter.parse(this)
    val dateParsed = LocalDate.from(date)
    val difference = Period.between(dateParsed, LocalDate.now())
    return difference.days
}

fun String?.tryFormatDate(
    inputFormat: String = FULL_DATE_MICROSECONDS,
    outputFormat: String = FULL_DATE
): String {
    if (this == "" || this == null) return ""
    val dateInputFormat = DateTimeFormatter.ofPattern(inputFormat)
    val dateOutputFormat = DateTimeFormatter.ofPattern(outputFormat)
    return try {
        val date = dateInputFormat.parse(this)
        dateOutputFormat.format(date)
    } catch(e: DateTimeParseException) {
        this
    }
}