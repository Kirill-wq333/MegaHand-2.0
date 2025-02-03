package com.evothings.domain.feature.card.interactor

import com.evothings.domain.feature.card.model.Transaction
import com.evothings.domain.feature.card.repository.CardRepository
import com.evothings.domain.util.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class CardInteractor(private val repository: CardRepository) {

    suspend fun getCardInfo(forceOnline: Boolean, offlineMode: Boolean = false, city: String) =
        repository.getCardInfo(forceOnline, offlineMode, city)

    suspend fun getRawTransactions(force: Boolean, offlineMode: Boolean): Result<List<Transaction>> =
        if (!offlineMode) {
            repository.getCardTransactions(force)
        } else Result.success(emptyList())


    fun sortTransactions(list: List<Transaction>): Map<String, List<Transaction>> {
        val operationDateFormat = DateTimeFormatter.ofPattern(DateFormat.FULL_DATE_PRECISE)
        val sortFullDateFormat = DateTimeFormatter.ofPattern(DateFormat.FULL_DATE)
        val sortFormat = DateTimeFormatter.ofPattern(DateFormat.DAY_MONTH)
        val timeFormat = DateTimeFormatter.ofPattern(DateFormat.CLOCK)

        val dates = list.map { it.date }
        val map: HashMap<LocalDate, List<Transaction>> = hashMapOf()

        dates.forEach { date ->
            val parsedDate = operationDateFormat.parse(date)
            val localDate = LocalDate.from(parsedDate)

            val transactionsWithSameDate =
                list.filter {
                    val parsedTransactionDate = operationDateFormat.parse(it.date)
                    val transactionLocalDate = LocalDate.from(parsedTransactionDate)

                    localDate.isEqual(transactionLocalDate)
                }

            val timeFormattedTransactions =
                transactionsWithSameDate.map {
                    val parsed = operationDateFormat.parse(it.date)
                    val localDateTime = LocalDateTime.from(parsed)

                    val userTimeZoneId = ZoneOffset.systemDefault()
                    val zoneOffset = userTimeZoneId.rules.getOffset(localDateTime)
                    val dateWithOffset = localDateTime.plusSeconds(zoneOffset.totalSeconds.toLong())

                    it.copy(
                        date = dateWithOffset.format(timeFormat)
                    )
                }

            map.putIfAbsent(localDate, timeFormattedTransactions)
        }

        val withKeysSorted = map.toSortedMap(compareByDescending { it })
        return withKeysSorted.mapKeys { entry ->
            val today = LocalDate.now()
            val yesterday = today.minusDays(1)
            when {
                entry.key.isEqual(today) -> "Сегодня"
                entry.key.isEqual(yesterday) -> "Вчера"
                entry.key.year == today.year -> entry.key.format(sortFormat)
                else -> entry.key.format(sortFullDateFormat)
            }
        }
    }

}