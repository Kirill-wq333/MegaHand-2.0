package com.evothings.data.feature.notification.di

import com.evothings.data.feature.notification.NotificationRepositoryImpl
import com.evothings.data.feature.notification.datasource.NotificationNetworkClient
import com.evothings.data.storage.room.dao.NotificationDao
import com.evothings.domain.feature.auth.repository.AuthRepository
import com.evothings.domain.feature.notification.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotificationModule {

    @Provides
    @Singleton
    fun provideNotificationNetworkClient(retrofit: Retrofit): NotificationNetworkClient =
        retrofit.create(NotificationNetworkClient::class.java)

    @Provides
    @Singleton
    fun provideNotificationRepository(
        notificationDao: NotificationDao,
        authRepository: AuthRepository,
        networkClient: NotificationNetworkClient
    ): NotificationRepository =
        NotificationRepositoryImpl(notificationDao, authRepository, networkClient)

}