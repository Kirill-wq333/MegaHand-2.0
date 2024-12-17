package com.evothings.data.feature.card.dto.mapper

import com.evothings.data.feature.card.dto.CardResponse
import com.evothings.data.utils.date.tryFormatDate
import com.evothings.domain.feature.card.model.Card
import com.evothings.domain.util.DateFormat.FULL_DATE
import com.evothings.domain.util.DateFormat.ISO
import java.util.Calendar

internal fun CardResponse.toCard(): Card {
    return Card(
        balance = this.balance.toInt(),
        barcodeUrl = this.qrCodeUrl,
        burnAmount = this.burnableAmount?.toDouble() ?: 0.0,
        cashbackLevel = cashbackLevel,
        burnDate = formatBurnDate(burnDate)
    )
}

/*
 * Drop year from string if burn is in current year
 */
private fun formatBurnDate(date: String?): String {
    if (date == null) return ""
    val formatted = date.tryFormatDate(inputFormat = ISO, outputFormat = FULL_DATE)
    val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
    return if (formatted.takeLast(4) == currentYear) formatted.dropLast(5) else formatted
}