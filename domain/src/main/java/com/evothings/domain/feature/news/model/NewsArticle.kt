package com.evothings.domain.feature.news.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsArticle(
    val id: String = "",
    val title: String = "",
    val publishingDate: String = "",
    val categories: String = "",
    val articleLink: String = "",
    val content: String = "",
    val previewImageLink: String = ""
)