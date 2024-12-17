package com.evothings.data.feature.address

import com.evothings.data.feature.address.datasource.AddressApi
import com.evothings.data.feature.address.dto.PrimaryAddressRequest
import com.evothings.data.feature.address.dto.mapper.toAddressDataModel
import com.evothings.data.feature.address.dto.mapper.toAddressList
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.address.repository.AddressRepository

class AddressRepositoryImpl(
    private val api: AddressApi
) : AddressRepository {

    override suspend fun getUserAddresses(): Result<List<Address>> {
        return api.getAddressList().awaitResult()
            .mapCatching { it.results.toAddressList() }
    }

    override suspend fun createAddress(address: Address): Result<Unit> {
        return api.createAddress(address.toAddressDataModel()).awaitResult()
    }

    override suspend fun editAddress(address: Address): Result<Unit> {
        return api.editAddress(address.id, address.toAddressDataModel()).awaitResult()
    }

    override suspend fun deleteAddress(id: Int): Result<Unit> =
        runCatching { api.deleteAddress(id) }

    override suspend fun setPrimary(id: Int): Result<Unit> {
        return api.setAddressPrimary(id = id, payload = PrimaryAddressRequest(true)).awaitResult()
    }

}