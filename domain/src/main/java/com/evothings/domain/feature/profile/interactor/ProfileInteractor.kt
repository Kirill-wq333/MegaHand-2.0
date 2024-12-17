package com.evothings.domain.feature.profile.interactor

import com.evothings.domain.feature.address.repository.AddressRepository
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.feature.profile.repository.ProfileRepository

class ProfileInteractor(
    private val repository: ProfileRepository,
    private val addressRepository: AddressRepository,
) {

    suspend fun getProfile(forceOnline: Boolean) =
        repository.getProfileInfo(forceOnline)

    suspend fun getReferals(forceOnline: Boolean) =
        repository.getReferals(forceOnline)

    suspend fun getAddressList() =
        addressRepository.getUserAddresses()

    suspend fun getOrdersHistory() =
        repository.getOrdersHistory()

    suspend fun editProfile(newProfile: Profile) =
        repository.editProfile(newProfile)

    suspend fun logout() =
        repository.logout()

    suspend fun deleteAccount() =
        repository.deleteAccount()

    suspend fun confirmChangePhone(code: String, newPhone: String) =
        repository.confirmChangeNumber(code, newPhone)

    suspend fun sendCodeOnNewPhone(newPhone: String) =
        repository.sendCodeOnNewPhone(newPhone)

}