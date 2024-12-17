package com.evothings.data.feature.card

import com.evothings.data.feature.card.datasource.CardNetworkClient
import com.evothings.data.feature.card.dto.CardResponse
import com.evothings.data.feature.card.dto.TransactionResponse
import com.evothings.data.feature.card.dto.mapper.toCard
import com.evothings.data.feature.card.dto.mapper.toTransactionsList
import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.cache.fetchCache
import com.evothings.data.storage.room.dao.CardDao
import com.evothings.data.storage.room.entity.card.map
import com.evothings.data.storage.room.entity.card.toEntity
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.card.model.Card
import com.evothings.domain.feature.card.model.Transaction
import com.evothings.domain.feature.card.model.CardException
import com.evothings.domain.feature.card.repository.CardRepository

class CardRepositoryImpl(
    private val cardDao: CardDao,
    private val networkClient: CardNetworkClient,
    private val cacheStore: CacheStore
) : CardRepository {

    override suspend fun getCardInfo(force: Boolean, offlineMode: Boolean, city: String): Result<Card> {
        if (offlineMode) return fetchCardOffline()
        if (!loyalityIsAvailableInCity(city)) {
            return Result.failure(CardException.LoyalityNotAvailable)
        }
        val cardInfo = fetchCard(force)
        cardInfo.onSuccess {
            val entity = it.toEntity()
            cardDao.insertCard(entity)
        }
        return cardInfo
    }

    override suspend fun fetchCardOffline(): Result<Card> {
        return if (cardExistsInStorage()) {
            val card = cardDao.getCard().map()
            Result.success(card)
        } else Result.failure(CardException.NoSuchCard)
    }

    override suspend fun getCardTransactions(force: Boolean): Result<List<Transaction>> =
        fetchCache(
            forceOnline = force,
            cacheKey = NetworkConfig.Routes.Card.userTransactions,
            fetchFromNetwork = { networkClient.getUserTransactions().awaitResult() },
            cacheStore = cacheStore,
            mapper = Array<TransactionResponse>::toTransactionsList
        )

    private suspend fun loyalityIsAvailableInCity(city: String): Boolean =
        fetchCache(
            forceOnline = false,
            cacheKey = city,
            fetchFromNetwork = { networkClient.checkCityAvailable(city).awaitResult() },
            cacheStore = cacheStore,
            mapper = { this.exist }
        ).getOrDefault(false)

    private suspend fun fetchCard(force: Boolean): Result<Card> =
        fetchCache(
            forceOnline = force,
            cacheKey = NetworkConfig.Routes.Card.cardInfo,
            cacheStore = cacheStore,
            fetchFromNetwork = { networkClient.getCardInfo().awaitResult() },
            mapper = CardResponse::toCard
        )

    private suspend fun cardExistsInStorage(): Boolean =
        runCatching {
            val card = cardDao.getCard()
            card.toString()
        }.isSuccess

}