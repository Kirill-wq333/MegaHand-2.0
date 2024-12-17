package com.evothings.data.network.di

import com.evothings.data.network.interceptor.AuthInterceptor
import com.evothings.data.network.interceptor.DevModeInterceptor
import com.evothings.data.storage.room.dao.TokenStoreDao
import com.evothings.domain.feature.settings.repository.AppSettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenDao: TokenStoreDao): AuthInterceptor =
        AuthInterceptor(tokenDao)

    @Provides
    @Singleton
    fun provideDevModeInterceptor(appSettingsRepository: AppSettingsRepository): DevModeInterceptor =
        DevModeInterceptor(appSettingsRepository)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        devModeInterceptor: DevModeInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
//            .addInterceptor(devModeInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @Singleton
    fun provideRetrofit(builder: Retrofit.Builder): Retrofit =
        builder.baseUrl("http://localhost/").build()

}