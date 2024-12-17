package com.evothings.mhand.presentation.feature.shared.screen.chooseCity.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.settings.interactor.AppSettingsInteractor
import com.evothings.domain.feature.header.HeaderInteractor
import com.evothings.domain.feature.home.model.CitiesMap
import com.evothings.domain.feature.home.model.City
import com.evothings.mhand.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseCityViewModel @Inject constructor(
    private val appSettingsInteractor: AppSettingsInteractor,
    private val headerInteractor: HeaderInteractor
) : BaseViewModel<ChooseCityContract.Event, ChooseCityContract.State, Nothing>() {

    private val initialMap: MutableStateFlow<CitiesMap> = MutableStateFlow(emptyMap())

    private val _citiesMap: MutableStateFlow<CitiesMap> = MutableStateFlow(emptyMap())
    val citiesMap = _citiesMap.asStateFlow()

    override fun setInitialState(): ChooseCityContract.State = ChooseCityContract.State.Loading

    override fun handleEvent(event: ChooseCityContract.Event) = when(event) {
        is ChooseCityContract.Event.LoadCities -> loadCitiesList(event.showChosen)
        is ChooseCityContract.Event.ChooseCity -> chooseCity(event.newCity)
        is ChooseCityContract.Event.SearchCity -> searchCity(event.query)
    }

    private fun loadCitiesList(markChosen: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            setState(ChooseCityContract.State.Loading)

            headerInteractor.getCities().fold(
                onSuccess = { citiesMap ->
                    val withChosenCity =
                        getMapWithSelectedCity(citiesMap, markChosen, appSettingsInteractor.city)
                    initialMap.emit(withChosenCity)
                    _citiesMap.emit(withChosenCity)
                    updateState { ChooseCityContract.State.Loaded }
                },
                onFailure = {
                    updateState { ChooseCityContract.State.NetworkError }
                }
            )

        }
    }

    private suspend fun getMapWithSelectedCity(
        map: CitiesMap,
        markSelected: Boolean,
        selected: Flow<String>
    ): CitiesMap {
        if (!markSelected) return map
        val markedResult =
            runCatching {
                val city = selected.first()
                markSelectedCity(map, city)
            }
        return markedResult.getOrDefault(map)
    }

    private fun markSelectedCity(cities: CitiesMap, city: String): CitiesMap {
        val mutableMap = cities.toMutableMap()
        val key = city.take(1).uppercase()

        val list = mutableMap[key]!!
        val item = list.find { it.name == city && !it.chosen }
        val index = list.indexOf(item)
        mutableMap[key]!![index].chosen = true

        return mutableMap
    }

    private fun chooseCity(cityName: String) {
        _citiesMap.update { map ->
            val newMap = mutableMapOf<String, List<City>>()
            for (key in map.keys) {
                newMap[key] = map[key]!!.map { it.copy(chosen = false) }
            }
            val key = cityName.take(1)
            newMap[key]!!.first { it.name == cityName }.chosen = true
            newMap
        }
    }

    private fun searchCity(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query == "") { _citiesMap.emit(initialMap.value); return@launch }
            val key = query.take(1).uppercase()
            _citiesMap.update {
                val new = initialMap.value[key]?.filter {
                    it.name.startsWith(
                        query.replaceFirstChar { it.uppercase() }
                    )
                } ?: listOf()
                mapOf(key to new)
            }
        }
    }

}