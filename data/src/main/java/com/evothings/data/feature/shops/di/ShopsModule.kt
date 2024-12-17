package com.evothings.data.feature.shops.di

import com.evothings.data.feature.shops.ShopsRepositoryImpl
import com.evothings.data.feature.shops.datasource.ShopsNetworkClient
import com.evothings.data.storage.cache.CacheStore
import com.evothings.domain.feature.shops.repository.ShopsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ShopsModule {

    @Provides
    @Singleton
    fun provideShopsNetworkClient(retrofit: Retrofit): ShopsNetworkClient =
        retrofit.create(ShopsNetworkClient::class.java)

    @Provides
    @Singleton
    fun provideShopsRepository(networkClient: ShopsNetworkClient, cacheStore: CacheStore): ShopsRepository =
        ShopsRepositoryImpl(networkClient, cacheStore)

}