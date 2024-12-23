package com.evothings.mhand.presentation.feature.address.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.address.interactor.AddressInteractor
import com.evothings.domain.feature.address.model.Address
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressInteractor: AddressInteractor
) : ViewModel() {

    fun createAddress(model: Address) {
        viewModelScope.launch(Dispatchers.IO) {
            addressInteractor.createAddress(model)
        }
    }

    fun editAddress(model: Address) {
        viewModelScope.launch(Dispatchers.IO) {
            addressInteractor.updateAddress(model)
        }
    }

    fun deleteAddress(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addressInteractor.delete(id)
        }
    }

    fun setAddressPrimary(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addressInteractor.setAddressPrimary(id)
        }
    }

}