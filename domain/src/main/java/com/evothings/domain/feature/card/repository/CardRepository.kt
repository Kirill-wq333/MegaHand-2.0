package com.evothings.domain.feature.card.repository

import com.evothings.domain.feature.card.model.Card
import com.evothings.domain.feature.card.model.Transaction

interface CardRepository {
    suspend fun getCardInfo(force: Boolean, offlineMode: Boolean, city: String): Result<Card>
    suspend fun fetchCardOffline(): Result<Card>
    suspend fun getCardTransactions(force: Boolean): Result<List<Transaction>>
}