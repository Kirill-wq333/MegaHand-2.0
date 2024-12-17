package com.evothings.data.feature.product.di

import com.evothings.data.feature.product.ProductRepositoryImpl
import com.evothings.data.feature.product.datasource.ProductApi
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.room.dao.ProductsDao
import com.evothings.domain.feature.auth.repository.AuthRepository
import com.evothings.domain.feature.product.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ProductModule {

    @Provides
    fun provideProductsApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    fun provideProductRepository(
        productApi: ProductApi,
        productsDao: ProductsDao,
        authRepository: AuthRepository,
        cacheStore: CacheStore
    ): ProductRepository {
        return ProductRepositoryImpl(productApi, authRepository, productsDao, cacheStore)
    }

}