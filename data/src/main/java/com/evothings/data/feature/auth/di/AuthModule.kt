package com.evothings.data.feature.auth.di

import com.evothings.data.feature.auth.AuthRepositoryImpl
import com.evothings.data.feature.auth.datasource.AuthNetworkClient
import com.evothings.data.feature.auth.storageManager.UnauthorizedStorageManager
import com.evothings.data.storage.room.dao.TokenStoreDao
import com.evothings.domain.feature.auth.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideAuthNetworkClient(retrofit: Retrofit): AuthNetworkClient =
        retrofit.create(AuthNetworkClient::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(
        client: AuthNetworkClient,
        unauthorizedStorageManager: UnauthorizedStorageManager,
        tokenDao: TokenStoreDao
    ): AuthRepository =
        AuthRepositoryImpl(client, unauthorizedStorageManager, tokenDao)

}