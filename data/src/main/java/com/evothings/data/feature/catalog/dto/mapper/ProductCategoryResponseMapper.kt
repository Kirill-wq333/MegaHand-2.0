package com.evothings.data.feature.catalog.dto.mapper

import com.evothings.data.feature.catalog.dto.ProductCategoryResponse
import com.evothings.domain.feature.catalog.model.ProductCategory

internal fun ProductCategoryResponse.toProductCategory(): ProductCategory {
    return ProductCategory(
        id = id,
        title = title,
        parent = parentCategory?.toProductCategory(),
        children = children?.map { it.toProductCategory() },
        photoLink = photoLink
    )
}