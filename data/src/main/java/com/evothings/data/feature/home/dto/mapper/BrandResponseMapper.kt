package com.evothings.data.feature.home.dto.mapper

import com.evothings.data.feature.home.dto.response.BrandResponse
import com.evothings.domain.feature.home.model.Brand

internal fun BrandResponse.toBrand(): Brand {
    return Brand(
        id = id,
        name = name,
        photoLink = logo
    )
}