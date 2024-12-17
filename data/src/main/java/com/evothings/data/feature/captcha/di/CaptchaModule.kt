package com.evothings.data.feature.captcha.di

import com.evothings.data.feature.captcha.CaptchaRepositoryImpl
import com.evothings.data.feature.captcha.datasource.CaptchaNetworkClient
import com.evothings.domain.feature.captcha.repository.CaptchaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CaptchaModule {

    @Provides
    @Singleton
    fun provideCaptchaNetworkClient(retrofit: Retrofit): CaptchaNetworkClient =
        retrofit.create(CaptchaNetworkClient::class.java)

    @Provides
    @Singleton
    fun provideCaptchaRepository(networkClient: CaptchaNetworkClient): CaptchaRepository =
        CaptchaRepositoryImpl(networkClient)

}