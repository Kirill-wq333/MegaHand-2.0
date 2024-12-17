package com.evothings.mhand.presentation.utils.sdkutil

import com.evothings.domain.util.DateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

internal fun ageFromDate(birthday: String): Int {
    val dateFormat = if (birthday.contains(".")) DateFormat.FULL_DATE else DateFormat.WESTERN_DATE
    val format = DateTimeFormatter.ofPattern(dateFormat)
    val date = try {
        val parsedDate = format.parse(birthday)
        LocalDate.from(parsedDate)
    } catch(e: DateTimeParseException) {
        return 0
    }
    val todayDate = LocalDate.now()
    val difference = Period.between(date, todayDate)
    return difference.years
}