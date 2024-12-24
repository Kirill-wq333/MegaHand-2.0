package com.evothings.mhand.presentation.feature.address.viewmodel.register

import com.evothings.domain.feature.address.model.Address

object AddressNavigationRegistry {

    private var value: Address? = null

    fun get(): Address? = value

    fun set(details: Address) {
        value = details
    }

    fun clear() {
        value = null
    }

}