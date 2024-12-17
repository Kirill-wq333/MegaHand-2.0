package com.evothings.data.feature.coupon.di

import com.evothings.data.feature.coupon.CouponRepositoryImpl
import com.evothings.data.feature.coupon.datasource.CouponApiClient
import com.evothings.data.storage.cache.CacheStore
import com.evothings.domain.feature.coupon.repository.CouponRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CouponModule {

    @Provides
    @Singleton
    fun provideCouponApiClient(retrofit: Retrofit): CouponApiClient =
        retrofit.create(CouponApiClient::class.java)

    @Provides
    @Singleton
    fun provideCouponRepository(apiClient: CouponApiClient, cacheStore: CacheStore): CouponRepository =
        CouponRepositoryImpl(apiClient, cacheStore)

}