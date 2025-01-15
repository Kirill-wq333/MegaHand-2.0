package com.evothings.mhand.presentation.feature.profile.ui

import androidx.compose.runtime.Stable
import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.profile.model.Order
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.feature.profile.model.ReferalInfo

private data class ProfileUiState(
    val profile: Profile = Profile(),
    val addresses: List<Address> = listOf(),
    val referalInfo: ReferalInfo = ReferalInfo(),
    val orders: List<Order> = listOf(),
    val query: String = "",
    val dateQuery: String = "",
)

interface ProfileCallback {
    fun reload() {}
    fun updateProfile(profile: Profile) {}
    fun confirmPhone(newPhone: String) {}
    fun logout() {}
    fun deleteAccount() {}
    fun searchOrders(query: String) {}
    fun getOrdersByDate(date: String) {}
    fun continueOrderCheckout(orderId: String) {}
    fun payForOrder(paymentLink: String?) {}
    fun openProductInfoScreen(id: Int) {}
    fun copyOrderTrack(track: String) {}
    fun openAddressMap(city: String) {}
    fun openCatalogScreen() {}
    fun toggleContentState() {}
    fun disableOnboarding() {}
}

@Stable
private object EmptyProfileCallback : ProfileCallback
