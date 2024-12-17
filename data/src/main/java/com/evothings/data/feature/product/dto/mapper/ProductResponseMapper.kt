package com.evothings.data.feature.product.dto.mapper

import com.evothings.data.feature.product.dto.response.ProductResponse
import com.evothings.data.feature.product.dto.response.PropertyResponse
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.feature.product.model.ProductProperty

internal fun ProductResponse.toProduct(): Product {
    return Product(
        id = id,
        title = name,
        photos = photos.map { it.photo },
        actualPrice = price.toDouble(),
        oldPrice = oldPrice.toDouble(),
        discount = discount.toDouble(),
        isPercentDiscount = isPercentDiscount,
        cashbackPoints = (bonusesCount?.toDouble() ?: 0.0),
        size = size,
        color = color,
        brandId = brand,
        description = description,
        condition = quality,
        properties = properties.toProductProperties(),
        availability = inStock.toProductAvailability(),
        isInCart = isInCart,
        isFavourite = isFavourite
    )
}

private fun Boolean.toProductAvailability(): Product.Availability =
    if (this) Product.Availability.IN_STOCK else Product.Availability.OUT_OF_STOCK

private fun Array<PropertyResponse>.toProductProperties(): List<ProductProperty> {
    return this.map { ProductProperty(name = it.type.name, value = it.value.value) }
}