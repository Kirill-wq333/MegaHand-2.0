package com.evothings.data.storage.di

import android.content.Context
import androidx.room.Room
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.dataStore.DataStoreClient
import com.evothings.data.storage.room.MhandDatabase
import com.evothings.data.storage.room.dao.CardDao
import com.evothings.data.storage.room.dao.NotificationDao
import com.evothings.data.storage.room.dao.ProductsDao
import com.evothings.data.storage.room.dao.TokenStoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): MhandDatabase =
        Room.databaseBuilder(context, MhandDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideCacheStore(): CacheStore = CacheStore()

    @Provides
    @Singleton
    fun provideDataStoreClient(@ApplicationContext context: Context): DataStoreClient =
        DataStoreClient(context)

    @Provides
    @Singleton
    fun provideTokenStoreDao(database: MhandDatabase): TokenStoreDao = database.getTokenDao()

    @Provides
    @Singleton
    fun provideCardDao(database: MhandDatabase): CardDao = database.getCardDao()

    @Provides
    @Singleton
    fun provideNotificationDao(database: MhandDatabase): NotificationDao = database.getNotificationsDao()

    @Provides
    @Singleton
    fun provideProductsDao(database: MhandDatabase): ProductsDao = database.getProductsDao()

}