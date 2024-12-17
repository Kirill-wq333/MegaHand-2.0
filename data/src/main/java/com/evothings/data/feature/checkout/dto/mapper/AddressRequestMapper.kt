package com.evothings.data.feature.checkout.dto.mapper

import com.evothings.data.feature.checkout.dto.request.AddressRequest

internal fun String.toAddressRequest(): AddressRequest = AddressRequest(address = this)