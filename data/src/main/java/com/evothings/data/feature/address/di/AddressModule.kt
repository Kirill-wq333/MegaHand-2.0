package com.evothings.data.feature.address.di

import com.evothings.data.feature.address.AddressRepositoryImpl
import com.evothings.data.feature.address.datasource.AddressApi
import com.evothings.domain.feature.address.repository.AddressRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AddressModule {

    @Provides
    fun provideAddressApi(retrofit: Retrofit): AddressApi = retrofit.create(AddressApi::class.java)

    @Provides
    fun provideAddressRepository(addressApi: AddressApi): AddressRepository =
        AddressRepositoryImpl(addressApi)

}