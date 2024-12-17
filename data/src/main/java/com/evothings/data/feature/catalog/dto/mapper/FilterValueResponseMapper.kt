package com.evothings.data.feature.catalog.dto.mapper

import com.evothings.data.feature.catalog.dto.FilterValueResponse
import com.evothings.domain.feature.catalog.model.FilterValue

internal fun FilterValueResponse.toFilterValue(): FilterValue {
    return FilterValue(
        id = id,
        value = value
    )
}