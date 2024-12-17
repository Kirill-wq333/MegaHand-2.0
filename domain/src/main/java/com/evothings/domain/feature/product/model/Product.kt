package com.evothings.domain.feature.product.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val photos: List<String> = listOf(),
    val actualPrice: Double = 0.0,
    val oldPrice: Double = 0.0,
    val discount: Double = 0.0,
    val isPercentDiscount: Boolean = false,
    val cashbackPoints: Double = 0.0,
    val brandId: Int? = 0,
    val size: String = "",
    val categoryId: Int = 0,
    val color: String = "",
    val condition: String = "",
    val properties: List<ProductProperty> = emptyList(),
    val availability: Availability = Availability.OUT_OF_STOCK,
    val isInCart: Boolean = false,
    val isFavourite: Boolean = false,
) {

    enum class Availability {
        IN_STOCK, OUT_OF_STOCK
    }

}
