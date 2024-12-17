package com.evothings.data.feature.home.di

import com.evothings.data.feature.home.HomeRepositoryImpl
import com.evothings.data.feature.home.datasource.HomeNetworkClient
import com.evothings.data.storage.cache.CacheStore
import com.evothings.domain.feature.home.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Provides
    @Singleton
    fun provideHomeNetworkClient(retrofit: Retrofit): HomeNetworkClient =
        retrofit.create(HomeNetworkClient::class.java)

    @Provides
    @Singleton
    fun provideHomeRepository(networkClient: HomeNetworkClient, cacheStore: CacheStore): HomeRepository =
        HomeRepositoryImpl(networkClient, cacheStore)

}