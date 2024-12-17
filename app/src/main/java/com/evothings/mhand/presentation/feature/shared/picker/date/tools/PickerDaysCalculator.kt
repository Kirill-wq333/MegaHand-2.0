package com.evothings.mhand.presentation.feature.shared.picker.date.tools

import com.evothings.mhand.presentation.feature.shared.picker.date.model.Day
import java.util.Calendar

class PickerDaysCalculator(private val timestamp: Long) {

    fun getDays(): List<Day> {
        val days: ArrayList<Day> = arrayListOf()
        days.addAll(getPreviousMonthDays())
        days.addAll(getCurrentMonthDays())

        val selectedDay = getSelectedDay()
        days.replaceAll { item ->
            if (item.number == selectedDay) {
                item.copy(
                    isSelected = true
                )
            } else item
        }
        days.addAll(getNextMonthDays())
        return days
    }

    private fun getPreviousMonthDays(): List<Day> {
        val cal = getCalendarForTimestamp()
        val currentMonth = cal.get(Calendar.MONTH)
        cal.set(Calendar.MONTH, currentMonth - 1)
        val prevMonthMaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val offset = getFirstWeekOffset()
        val startDay = prevMonthMaxDay - offset + 1
        val range = startDay..prevMonthMaxDay

        return range.toList().map { day ->
            Day(
                number = day,
                isActive = false,
                isSelected = false
            )
        }
    }

    private fun getCurrentMonthDays(): List<Day> {
        val cal = getCalendarForTimestamp()
        val curMonthMaxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val range = 1..curMonthMaxDays
        return range.toList().map { day ->
            Day(
                number = day,
                isActive = true,
                isSelected = false
            )
        }
    }

    private fun getSelectedDay(): Int {
        val cal = getCalendarForTimestamp()
        return cal.get(Calendar.DAY_OF_MONTH)
    }

    private fun getNextMonthDays(): List<Day> {
        val endDay = getLastWeekOffset()
        val range =  1..endDay + 1
        return range.toList().map { day ->
            Day(
                number = day,
                isActive = false,
                isSelected = false
            )
        }
    }

    private fun getFirstWeekOffset(): Int {
        val cal = getCalendarForTimestamp()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        return getLeadingDaysOffset(cal)
    }

    private fun getLastWeekOffset(): Int {
        val cal = getCalendarForTimestamp()
        val maximumDaysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        cal.set(Calendar.DAY_OF_MONTH, maximumDaysOfMonth)
        return getTrailingCellsOffset(cal)
    }

    private fun getCalendarForTimestamp(): Calendar =
        Calendar.getInstance()
            .apply { this.timeInMillis = timestamp }

    private fun getLeadingDaysOffset(calendar: Calendar): Int {
        val dayOfWeekID = calendar.get(Calendar.DAY_OF_WEEK)
        println(dayOfWeekID)
        return if (dayOfWeekID == Calendar.SUNDAY) 6 else dayOfWeekID - 2
    }

    private fun getTrailingCellsOffset(calendar: Calendar): Int {
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return if (dayOfWeek == Calendar.SUNDAY) 0 else 7 - dayOfWeek
    }

}