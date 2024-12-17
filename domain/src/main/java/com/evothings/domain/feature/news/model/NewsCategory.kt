package com.evothings.domain.feature.news.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsCategory(
    val id: Int,
    val name: String = ""
)