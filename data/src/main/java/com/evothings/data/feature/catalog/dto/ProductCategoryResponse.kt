package com.evothings.data.feature.catalog.dto

import com.google.gson.annotations.SerializedName

data class ProductCategoryResponse(
    val id: Int,
    @SerializedName("name") val title: String,
    @SerializedName("photo") val photoLink: String?,
    @SerializedName("parent") val parentCategory: ProductCategoryResponse?,
    @SerializedName("children") val children: List<ProductCategoryResponse>?
)
