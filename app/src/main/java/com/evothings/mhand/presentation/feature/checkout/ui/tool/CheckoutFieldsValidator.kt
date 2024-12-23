package com.evothings.mhand.presentation.feature.checkout.ui.tool

import android.util.Patterns
import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.checkout.model.DeliveryOption

object CheckoutFieldsValidator {

    fun validatePersonal(name: String, surname: String, phone: String, email: String): Boolean {
        val fullNameValid = name.isNotEmpty() && surname.isNotEmpty()
        val phoneValid = phone.length == 11
        val emailRegex = Patterns.EMAIL_ADDRESS.toRegex()
        val emailValid = email.matches(emailRegex)
        return fullNameValid && phoneValid && emailValid
    }

    fun validateAddress(
        option: DeliveryOption,
        list: List<Address>,
        addressDetails: Address
    ): Boolean {
        val newAddressValid = with(addressDetails) {
            city.isNotEmpty() && street.isNotEmpty() && house.isNotEmpty() && flat.isNotEmpty()
        }
        return (option == DeliveryOption.PICK_UP) || (list.isNotEmpty() || newAddressValid)
    }

}