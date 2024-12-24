package com.evothings.mhand.presentation.feature.snackbar.di

import com.evothings.mhand.presentation.feature.snackbar.host.SnackbarItemHost
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SnackbarModule {

    @Provides
    @Singleton
    fun provideSnackbarItemHost(): SnackbarItemHost {
        return SnackbarItemHost()
    }

}