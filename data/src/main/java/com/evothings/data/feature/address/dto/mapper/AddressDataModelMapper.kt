package com.evothings.data.feature.address.dto.mapper

import com.evothings.data.feature.address.dto.AddressDataModel
import com.evothings.domain.feature.address.model.Address

internal fun Array<AddressDataModel>.toAddressList(): List<Address> {
    return this.map { it.toAddress() }
}

internal fun AddressDataModel.toAddress(): Address {
    return Address(
        id = id,
        fullAddress = address,
        city = city,
        street = street,
        house = house,
        flat = apartment.orEmpty(),
        postalCode = postalCode.orEmpty(),
        main = main ?: false
    )
}

internal fun Address.toAddressDataModel(): AddressDataModel {
    return AddressDataModel(
        id = id,
        address = joinToFullAddress(),
        city = city,
        house = house,
        apartment = flat,
        postalCode = postalCode,
        street = street,
        main = main
    )
}

private fun Address.joinToFullAddress(): String {
    val sb = StringBuilder()
    if (postalCode.isNotEmpty()) {
        sb.append("${postalCode}, ")
    }
    sb.append("$city, ")
    sb.append("$street, ")
    sb.append("$house, ")
    sb.append(flat)
    return sb.toString()
}