package com.evothings.data.feature.product.dto.response

data class PropertyResponse(
    val type: Type,
    val value: Value
)

data class Type(
    val id: Int,
    val name: String
)

data class Value(
    val id: Int,
    val value: String
)