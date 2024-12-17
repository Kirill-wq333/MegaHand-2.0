package com.evothings.data.feature.product.dto.mapper

import com.evothings.data.feature.product.dto.request.ProductId

internal fun Int.toProductIdRequest(): ProductId = ProductId(this)