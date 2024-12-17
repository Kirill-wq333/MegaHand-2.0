package com.evothings.data.feature.card.di

import com.evothings.data.feature.card.CardRepositoryImpl
import com.evothings.data.feature.card.datasource.CardNetworkClient
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.room.dao.CardDao
import com.evothings.domain.feature.card.repository.CardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CardModule {

    @Provides
    @Singleton
    fun provideCardNetworkClient(retrofit: Retrofit): CardNetworkClient =
        retrofit.create(CardNetworkClient::class.java)

    @Provides
    @Singleton
    fun provideCardRepository(
        dao: CardDao,
        networkClient: CardNetworkClient,
        cacheStore: CacheStore
    ): CardRepository =
        CardRepositoryImpl(dao, networkClient, cacheStore)

}