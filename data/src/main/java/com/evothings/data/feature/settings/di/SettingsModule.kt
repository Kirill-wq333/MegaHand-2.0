package com.evothings.data.feature.settings.di

import com.evothings.data.feature.settings.AppSettingsRepositoryImpl
import com.evothings.data.storage.dataStore.DataStoreClient
import com.evothings.domain.feature.settings.repository.AppSettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {

    @Provides
    @Singleton
    fun provideAppSettingsRepository(dataStore: DataStoreClient): AppSettingsRepository =
        AppSettingsRepositoryImpl(dataStore)

}