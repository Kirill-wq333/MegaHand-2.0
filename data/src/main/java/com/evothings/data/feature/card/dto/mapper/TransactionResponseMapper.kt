package com.evothings.data.feature.card.dto.mapper

import com.evothings.data.feature.card.dto.TransactionResponse
import com.evothings.domain.feature.card.model.Transaction
import com.evothings.domain.feature.card.model.TransactionType

internal fun Array<TransactionResponse>.toTransactionsList(): List<Transaction> {
    return this.map { response ->
        Transaction(
            type = convertTransactionType(response.type),
            amount = response.amount.toDouble().formatTransactionAmount(),
            date = response.transactionDate
        )
    }
}

private fun Double.formatTransactionAmount(): Double {
    val divisionRate = 100.0
    return (this / divisionRate)
}

private fun convertTransactionType(type: String): TransactionType =
    when (type) {
        "CHARGE" -> TransactionType.DEPOSIT
        "WRITE_OFF" -> TransactionType.WITHDRAW
        else -> TransactionType.UNDEFINED
    }