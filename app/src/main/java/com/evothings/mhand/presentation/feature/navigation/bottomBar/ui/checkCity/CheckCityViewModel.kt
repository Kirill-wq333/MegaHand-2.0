package com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.checkCity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.settings.interactor.AppSettingsInteractor
import com.evothings.domain.feature.home.interactor.HomeInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckCityViewModel @Inject constructor(
    private val appSettingsInteractor: AppSettingsInteractor,
    private val homeInteractor: HomeInteractor
) : ViewModel() {

    private val _bottomSheetEnabled = MutableStateFlow(false)
    val bottomSheetEnabled = _bottomSheetEnabled.asStateFlow()

    fun transliterateAndSaveCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            homeInteractor.transliterateCityName(city).fold(
                onSuccess = {
                    if (it != "") {
                        saveCity(it)
                        enableBottomSheet()
                    }
                },
                onFailure = {}
            )
        }
    }

    private fun saveCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            appSettingsInteractor.setCity(city)
        }
    }

    private fun enableBottomSheet() = _bottomSheetEnabled.update { true }

    fun disableBottomSheet() = _bottomSheetEnabled.update { false }


}