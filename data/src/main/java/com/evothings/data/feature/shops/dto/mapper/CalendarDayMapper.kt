package com.evothings.data.feature.shops.dto.mapper

import com.evothings.data.feature.shops.dto.CalendarDay
import com.evothings.domain.feature.shops.model.DiscountDay
import com.evothings.domain.feature.shops.model.enumeration.DiscountType
import com.evothings.domain.util.DateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

internal class CalendarDayMapper(private val shopProductAdditionDays: Array<Int>) {

    private val dateFormat = DateTimeFormatter.ofPattern(DateFormat.FULL_DATE)

    fun mapToDiscountCalendar(daysResponse: Array<CalendarDay>): List<DiscountDay> {
        val result: ArrayList<DiscountDay> = arrayListOf()
        result.addAll(getPreviousMonthDays())
        for (day in daysResponse) {
            val discountType = getDiscountType(day)
            result.add(
                DiscountDay(
                    dayOfMonth = getDayOfMonth(day.day),
                    dayOfWeek = getDayOfWeek(day.day),
                    discount = getDiscountDisplay(day.sale, discountType),
                    isActive = isDayInCurrentMonth(day.day),
                    isToday = isDayToday(day.day),
                    hasAddition = isHasAddition(day.day),
                    type = discountType
                )
            )
        }
        return result
    }

    private fun getPreviousMonthDays(): List<DiscountDay> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstWeekOffset = getPreviousMonthDaysInFirstWeekCount(calendar)
        val currentMonthFirstDay = LocalDate.of(
            calendar.get(Calendar.YEAR),
            (calendar.get(Calendar.MONTH) + 1),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        val previousMonthStartDate = currentMonthFirstDay.minusDays(firstWeekOffset.toLong())

        var date = previousMonthStartDate
        val previousMonthDays: ArrayList<DiscountDay> = arrayListOf()
        for (i in 0 until firstWeekOffset) {
            previousMonthDays.add(
                DiscountDay(
                    dayOfMonth = date.dayOfMonth,
                    dayOfWeek = date.dayOfWeek.getDisplay(),
                    isActive = false
                )
            )
            date = date.plusDays(1)
        }

        return previousMonthDays
    }

    private fun getPreviousMonthDaysInFirstWeekCount(calendar: Calendar): Int {
        val dayOfWeekOrdinal = calendar.get(Calendar.DAY_OF_WEEK)
        return if (dayOfWeekOrdinal == Calendar.SUNDAY) 6 else dayOfWeekOrdinal - 2
    }

    private fun getDayOfMonth(date: String): Int {
        val localDate = date.getLocalDate()
        return localDate.dayOfMonth
    }

    private fun getDayOfWeek(date: String): String {
        val localDate = date.getLocalDate()
        return localDate.dayOfWeek.getDisplay()
    }

    private fun getDiscountType(item: CalendarDay): DiscountType {
        val discountInRubles = (item.sale.length > 2 || item.sale.contains("руб"))
        val ticket = (item.sale.startsWith("БИЛЕТ"))
        val blackFriday = (item.sale == "ЧП")
        val withoutDiscount = (item.sale == "без скидок")
        val isNew = (item.sale == "0" && isHasAddition(item.day))
        val isNewDiscountSystem = item.sale.equals("Новая система скидок", ignoreCase = true)

        return when {
            isNewDiscountSystem -> DiscountType.NEW_DISCOUNT_SYSTEM
            isNew -> DiscountType.NEW
            withoutDiscount -> DiscountType.WITHOUT_DISCOUNT
            blackFriday -> DiscountType.BLACK_FRIDAY
            ticket -> DiscountType.TICKET
            discountInRubles -> DiscountType.ROUBLES
            else -> DiscountType.BY_PERCENTS
        }
    }

    private fun isDayInCurrentMonth(date: String): Boolean {
        val localDate = date.getLocalDate()
        val today = LocalDate.now()
        return localDate.month == today.month
    }

    private fun isHasAddition(date: String): Boolean {
        val dayOfMonth = getDayOfMonth(date)
        return dayOfMonth in shopProductAdditionDays
    }

    private fun isDayToday(date: String): Boolean {
        val localDate = date.getLocalDate()
        val today = LocalDate.now()
        return localDate.isEqual(today)
    }

    private fun getDiscountDisplay(rawDiscount: String, type: DiscountType): String {
        return when(type) {
            DiscountType.NEW -> "Завоз"
            DiscountType.BY_PERCENTS -> rawDiscount
            DiscountType.ROUBLES -> rawDiscount.filter { it.isDigit() }
            DiscountType.BLACK_FRIDAY -> "ЧП"
            DiscountType.WITHOUT_DISCOUNT -> "Без скидки"
            DiscountType.TICKET -> "Билет"
            DiscountType.NEW_DISCOUNT_SYSTEM -> "Новая система"
        }
    }

    private fun String.getLocalDate(): LocalDate {
        val parsedDate = dateFormat.parse(this)
        return LocalDate.from(parsedDate)
    }

    private fun DayOfWeek.getDisplay(): String {
        return this.getDisplayName(
            /* textStyle */ TextStyle.SHORT,
            /* locale */ Locale.forLanguageTag("ru")
        ).replaceFirstChar { it.uppercase() }
    }

}

fun main() {
    val calendarDays = arrayOf(
        CalendarDay(day = "01.11.2024", sale = "90"),
        CalendarDay(day = "02.11.2024", sale = "0"),
        CalendarDay(day = "03.11.2024", sale = "0"),
        CalendarDay(day = "04.11.2024", sale = "0"),
        CalendarDay(day = "05.11.2024", sale = "10"),
        CalendarDay(day = "06.11.2024", sale = "10"),
        CalendarDay(day = "07.11.2024", sale = "10"),
        CalendarDay(day = "08.11.2024", sale = "20"),
        CalendarDay(day = "09.11.2024", sale = "20"),
        CalendarDay(day = "10.11.2024", sale = "30"),
        CalendarDay(day = "11.11.2024", sale = "30"),
        CalendarDay(day = "12.11.2024", sale = "40"),
        CalendarDay(day = "13.11.2024", sale = "40"),
        CalendarDay(day = "14.11.2024", sale = "40"),
        CalendarDay(day = "15.11.2024", sale = "50"),
        CalendarDay(day = "16.11.2024", sale = "50"),
        CalendarDay(day = "17.11.2024", sale = "50"),
        CalendarDay(day = "18.11.2024", sale = "60"),
        CalendarDay(day = "19.11.2024", sale = "60"),
        CalendarDay(day = "20.11.2024", sale = "70"),
        CalendarDay(day = "21.11.2024", sale = "80"),
        CalendarDay(day = "22.11.2024", sale = "90"),
        CalendarDay(day = "23.11.2024", sale = "0"),
        CalendarDay(day = "24.11.2024", sale = "0"),
        CalendarDay(day = "25.11.2024", sale = "0"),
        CalendarDay(day = "26.11.2024", sale = "10"),
        CalendarDay(day = "27.11.2024", sale = "10"),
        CalendarDay(day = "28.11.2024", sale = "10"),
        CalendarDay(day = "29.11.2024", sale = "20"),
        CalendarDay(day = "30.11.2024", sale = "20")
    )

    val mapper = CalendarDayMapper(arrayOf(2, 3, 23, 9))
    mapper.mapToDiscountCalendar(calendarDays).forEach { println(it.toString()) }
}