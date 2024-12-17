package com.evothings.domain.feature.address.interactor

import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.address.repository.AddressRepository

class AddressInteractor(private val repository: AddressRepository) {

    suspend fun getUserAddresses() = repository.getUserAddresses()

    suspend fun createAddress(address: Address) = repository.createAddress(address)

    suspend fun updateAddress(address: Address) = repository.editAddress(address)

    suspend fun setAddressPrimary(id: Int) = repository.setPrimary(id)

    suspend fun delete(id: Int) = repository.deleteAddress(id)

}