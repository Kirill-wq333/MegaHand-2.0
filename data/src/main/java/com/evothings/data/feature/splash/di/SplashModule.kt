package com.evothings.data.feature.splash.di

import com.evothings.data.feature.splash.SplashRepositoryImpl
import com.evothings.data.feature.splash.datasource.SplashNetworkClient
import com.evothings.domain.feature.splash.SplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SplashModule {

    @Provides
    @Singleton
    fun provideSplashNetworkClient(retrofit: Retrofit): SplashNetworkClient =
        retrofit.create(SplashNetworkClient::class.java)

    @Provides
    @Singleton
    fun provideSplashRepository(networkClient: SplashNetworkClient): SplashRepository =
        SplashRepositoryImpl(networkClient)

}