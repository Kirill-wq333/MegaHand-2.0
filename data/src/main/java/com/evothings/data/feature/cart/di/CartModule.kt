package com.evothings.data.feature.cart.di

import com.evothings.data.feature.cart.CartRepositoryImpl
import com.evothings.data.feature.cart.datasource.CartApi
import com.evothings.data.storage.room.dao.ProductsDao
import com.evothings.domain.feature.auth.repository.AuthRepository
import com.evothings.domain.feature.cart.repository.CartRepository
import com.evothings.domain.feature.product.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class CartModule {

    @Provides
    fun provideCartApi(retrofit: Retrofit): CartApi {
        return retrofit.create(CartApi::class.java)
    }

    @Provides
    fun provideCartRepository(
        cartApi: CartApi,
        productRepository: ProductRepository,
        productsDao: ProductsDao,
        authRepository: AuthRepository
    ): CartRepository {
        return CartRepositoryImpl(
            cartApi, authRepository, productsDao, productRepository
        )
    }

}