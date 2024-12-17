package com.evothings.data.feature.catalog.dto

import com.evothings.data.feature.product.dto.response.ProductResponse

data class SearchHintResponse(
    val products: Array<ProductResponse>,
    val categories: Array<ProductCategoryResponse>
)
