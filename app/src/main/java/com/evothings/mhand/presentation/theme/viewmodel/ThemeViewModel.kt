package com.evothings.mhand.presentation.theme.viewmodel

import androidx.lifecycle.ViewModel
import com.evothings.domain.feature.settings.interactor.AppSettingsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val appSettingsInteractor: AppSettingsInteractor,
) : ViewModel() {

    val isDarkThemeEnabled: Flow<Boolean>
        get() = appSettingsInteractor.isDarkThemeEnabled


}