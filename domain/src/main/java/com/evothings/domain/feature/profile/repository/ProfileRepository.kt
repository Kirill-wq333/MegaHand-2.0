package com.evothings.domain.feature.profile.repository

import com.evothings.domain.feature.profile.model.Order
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.feature.profile.model.ReferalInfo

interface ProfileRepository {
    suspend fun getProfileInfo(forceOnline: Boolean): Result<Profile>
    suspend fun getReferals(forceOnline: Boolean): Result<ReferalInfo>
    suspend fun getOrdersHistory(): Result<List<Order>>
    suspend fun logout()
    suspend fun deleteAccount()
    suspend fun editProfile(newProfile: Profile): Result<String?>
    suspend fun confirmChangeNumber(code: String, newPhone: String): Result<Boolean>
    suspend fun sendCodeOnNewPhone(phone: String): Result<Unit>
}