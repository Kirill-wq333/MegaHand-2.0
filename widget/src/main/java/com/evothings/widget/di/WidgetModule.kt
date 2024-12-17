package com.evothings.widget.di

import com.evothings.domain.feature.auth.repository.AuthRepository
import com.evothings.domain.feature.card.repository.CardRepository
import com.evothings.widget.viewmodel.MhandWidgetDataLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WidgetModule {

    @Provides
    @Singleton
    fun provideDataLoader(
        cardRepository: CardRepository,
        authRepository: AuthRepository
    ): MhandWidgetDataLoader =
        MhandWidgetDataLoader(cardRepository, authRepository)

}