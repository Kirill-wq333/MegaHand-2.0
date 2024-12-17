package com.evothings.data.feature.catalog.di

import com.evothings.data.feature.catalog.CatalogRepositoryImpl
import com.evothings.data.feature.catalog.datasource.CatalogApi
import com.evothings.data.storage.cache.CacheStore
import com.evothings.domain.feature.catalog.repository.CatalogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CatalogModule {

    @Provides
    @Singleton
    fun provideCatalogApi(retrofit: Retrofit): CatalogApi {
        return retrofit.create(CatalogApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCatalogRepository(
        catalogApi: CatalogApi,
        cacheStore: CacheStore,
    ): CatalogRepository {
        return CatalogRepositoryImpl(catalogApi, cacheStore)
    }

}