package com.evothings.data.feature.news.di

import com.evothings.data.feature.news.NewsRepositoryImpl
import com.evothings.data.feature.news.datasource.NewsNetworkClient
import com.evothings.data.storage.cache.CacheStore
import com.evothings.domain.feature.news.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NewsModule {

    @Provides
    @Singleton
    fun provideNewsNetworkClient(retrofit: Retrofit): NewsNetworkClient =
        retrofit.create(NewsNetworkClient::class.java)

    @Provides
    @Singleton
    fun provideNewsRepository(networkClient: NewsNetworkClient, cacheStore: CacheStore): NewsRepository =
        NewsRepositoryImpl(networkClient, cacheStore)

}