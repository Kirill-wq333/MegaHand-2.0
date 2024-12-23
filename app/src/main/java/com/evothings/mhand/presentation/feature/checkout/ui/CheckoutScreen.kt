package com.evothings.mhand.presentation.feature.checkout.ui

import com.evothings.domain.feature.checkout.model.CheckoutResult

private interface CheckoutCallback {
    fun onBack()
    fun refreshScreen()
    fun openPrivacyPolicy()
    fun onCheckout(result: CheckoutResult)
    fun onClickProduct(id: Int)
    fun onChangePickupCity(city: String)
    fun openAddressMap(city: String)
    fun updateAddressesList()
}