package com.evothings.mhand.presentation.feature.shops.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.settings.interactor.AppSettingsInteractor
import com.evothings.domain.feature.shops.interactor.ShopsInteractor
import com.evothings.domain.feature.shops.model.Shop
import com.evothings.mhand.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopsViewModel @Inject constructor(
    private val shopsInteractor: ShopsInteractor,
    private val appSettingsInteractor: AppSettingsInteractor,
) : BaseViewModel<ShopsContract.Event, ShopsContract.State, ShopsContract.Effect>() {

    private val _shopsList: MutableStateFlow<ImmutableList<Shop>> = MutableStateFlow(
        persistentListOf()
    )
    val shopsList = _shopsList.asStateFlow()

    private val city: Flow<String>
        get() = appSettingsInteractor.city

    override fun setInitialState(): ShopsContract.State = ShopsContract.State.Loading

    override fun handleEvent(event: ShopsContract.Event) = when(event) {
        is ShopsContract.Event.LoadData -> loadData()
        is ShopsContract.Event.Refresh -> loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            city.collectLatest { city ->
                loadShopsListByCity(city)
            }
        }
    }

    private suspend fun loadShopsListByCity(city: String) {
        shopsInteractor.getShops(city).fold(
            onSuccess = { shopsList ->
                if (shopsList.isEmpty()) {
                    updateState { ShopsContract.State.ServerError }
                    return@fold
                } else {
                    _shopsList.emit(shopsList.toPersistentList())
                }
                updateState { ShopsContract.State.Loaded }
            },
            onFailure = {
                Log.d("as", "loadShopsListByCity: ${it.message}")
                updateState { ShopsContract.State.ServerError }
                setEffect {
                    ShopsContract.Effect.ShowErrorToast(
                        "Произошла ошибка при получении списка магазинов. Детали: ${it.localizedMessage}"
                    )
                }
            }
        )
    }

}