package com.evothings.data.feature.checkout.dto.mapper

import com.evothings.data.feature.checkout.dto.request.AmountRequest

internal fun Int.toAmountRequest(): AmountRequest = AmountRequest(amount = this)