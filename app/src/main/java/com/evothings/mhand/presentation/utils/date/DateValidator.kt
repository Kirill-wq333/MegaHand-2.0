package com.evothings.mhand.presentation.utils.date

import com.evothings.domain.util.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateValidator {

    fun isAfterToday(date: String, format: String = DateFormat.FULL_DATE): Boolean {
        if (date.isEmpty()) return false
        val localDate = getLocalDate(date, format)
        val today = LocalDate.now()

        return localDate.isAfter(today)
    }

    fun isBeforeToday(date: String, format: String = DateFormat.FULL_DATE): Boolean {
        if (date.isEmpty()) return false
        val localDate = getLocalDate(date, format)
        val today = LocalDate.now()

        return localDate.isBefore(today)
    }

    private fun getLocalDate(date: String, format: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(format)
        val parsedDate = formatter.parse(date)
        return LocalDate.from(parsedDate)
    }

}