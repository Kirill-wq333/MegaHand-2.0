package com.evothings.data.feature.header.di

import com.evothings.data.feature.header.HeaderRepositoryImpl
import com.evothings.data.feature.header.datasource.HeaderNetworkClient
import com.evothings.data.storage.cache.CacheStore
import com.evothings.domain.feature.header.repository.HeaderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HeaderModule {

    @Provides
    @Singleton
    fun provideHeaderNetworkClient(retrofit: Retrofit): HeaderNetworkClient =
        retrofit.create(HeaderNetworkClient::class.java)

    @Singleton
    @Provides
    fun provideHeaderRepository(networkClient: HeaderNetworkClient, cacheStore: CacheStore): HeaderRepository =
        HeaderRepositoryImpl(networkClient, cacheStore)

}