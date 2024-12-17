package com.evothings.data.feature.profile.di

import com.evothings.data.feature.profile.ProfileRepositoryImpl
import com.evothings.data.feature.profile.datasource.ProfileNetworkClient
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.room.dao.CardDao
import com.evothings.data.storage.room.dao.TokenStoreDao
import com.evothings.domain.feature.profile.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {

    @Provides
    @Singleton
    fun provideProfileNetworkClient(retrofit: Retrofit): ProfileNetworkClient =
        retrofit.create(ProfileNetworkClient::class.java)

    @Provides
    @Singleton
    fun provideProfileRepository(
        networkClient: ProfileNetworkClient,
        tokenDao: TokenStoreDao,
        cardDao: CardDao,
        cacheStore: CacheStore
    ): ProfileRepository = ProfileRepositoryImpl(networkClient, cacheStore, tokenDao, cardDao)

}