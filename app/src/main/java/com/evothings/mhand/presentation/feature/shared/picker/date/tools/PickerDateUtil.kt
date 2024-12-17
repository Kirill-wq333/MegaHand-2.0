package com.evothings.mhand.presentation.feature.shared.picker.date.tools

import com.evothings.mhand.presentation.feature.shared.picker.date.model.Year
import java.time.LocalDate
import java.util.Calendar

object PickerDateUtil {

    fun setDay(initial: Long, dayOfMonth: Int): Long {
        val cal = getCalendar(initial)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        return cal.timeInMillis
    }

    fun setMonth(timestamp: Long, pushForward: Boolean): Long {
        val cal = getCalendar(timestamp)
        val currentMonth = cal.get(Calendar.MONTH)
        val monthToSet = if (pushForward) currentMonth + 1 else currentMonth - 1
        cal.set(Calendar.MONTH, monthToSet)
        return cal.timeInMillis
    }

    fun setYear(timestamp: Long, yearToSet: Int): Long {
        val cal = getCalendar(timestamp)
        cal.set(Calendar.YEAR, yearToSet)
        return cal.timeInMillis
    }

    fun timestampToLocalDate(epochMillis: Long): LocalDate {
        val cal = getCalendar(epochMillis)
        return with(cal) {
            val month = get(Calendar.MONTH) + 1
            LocalDate.of(
                get(Calendar.YEAR),
                month,
                get(Calendar.DAY_OF_MONTH)
            )
        }
    }

    fun getYearsList(selected: Int): List<Year> {
        val yearsRange = 1940..2040
        return yearsRange.toList()
            .map { year ->
                Year(
                    value = year,
                    isSelected = (selected == year)
                )
            }
    }

    private fun getCalendar(timestamp: Long): Calendar =
        Calendar.getInstance()
            .apply { this.timeInMillis = timestamp }

}