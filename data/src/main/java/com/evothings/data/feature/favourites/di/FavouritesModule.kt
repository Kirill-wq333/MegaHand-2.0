package com.evothings.data.feature.favourites.di

import com.evothings.data.feature.favourites.FavouritesRepositoryImpl
import com.evothings.data.feature.favourites.datasource.FavouritesApiClient
import com.evothings.data.storage.room.dao.ProductsDao
import com.evothings.domain.feature.auth.repository.AuthRepository
import com.evothings.domain.feature.favourites.repository.FavouritesRepository
import com.evothings.domain.feature.product.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavouritesModule {

    @Provides
    @Singleton
    fun provideFavouritesApiClient(retrofit: Retrofit) =
        retrofit.create(FavouritesApiClient::class.java)

    @Provides
    @Singleton
    fun provideFavouritesRepository(
        apiClient: FavouritesApiClient,
        authRepository: AuthRepository,
        productRepository: ProductRepository,
        productsDao: ProductsDao
    ): FavouritesRepository =
        FavouritesRepositoryImpl(apiClient, productRepository, productsDao, authRepository)

}