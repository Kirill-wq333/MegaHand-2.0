package com.evothings.domain.feature.address.repository

import com.evothings.domain.feature.address.model.Address

interface AddressRepository {
    suspend fun getUserAddresses(): Result<List<Address>>
    suspend fun createAddress(address: Address): Result<Unit>
    suspend fun setPrimary(id: Int): Result<Unit>
    suspend fun editAddress(address: Address): Result<Unit>
    suspend fun deleteAddress(id: Int): Result<Unit>
}