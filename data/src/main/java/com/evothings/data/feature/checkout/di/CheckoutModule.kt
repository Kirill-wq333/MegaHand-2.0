package com.evothings.data.feature.checkout.di

import com.evothings.data.feature.checkout.CheckoutRepositoryImpl
import com.evothings.data.feature.checkout.datasource.CheckoutApi
import com.evothings.domain.feature.address.repository.AddressRepository
import com.evothings.domain.feature.card.repository.CardRepository
import com.evothings.domain.feature.checkout.repository.CheckoutRepository
import com.evothings.domain.feature.product.repository.ProductRepository
import com.evothings.domain.feature.profile.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CheckoutModule {

    @Provides
    @Singleton
    fun provideCheckoutApi(retrofit: Retrofit): CheckoutApi {
        return retrofit.create(CheckoutApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCheckoutRepository(
        addressRepository: AddressRepository,
        profileRepository: ProfileRepository,
        cardRepository: CardRepository,
        productRepository: ProductRepository,
        checkoutApi: CheckoutApi
    ): CheckoutRepository =
        CheckoutRepositoryImpl(
            checkoutApi, profileRepository, addressRepository, cardRepository,
            productRepository
        )

}