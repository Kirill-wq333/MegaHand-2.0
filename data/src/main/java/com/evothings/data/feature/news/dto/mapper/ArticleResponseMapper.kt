package com.evothings.data.feature.news.dto.mapper

import com.evothings.data.feature.news.dto.ArticleResponse
import com.evothings.data.utils.date.tryFormatDate
import com.evothings.domain.feature.news.model.NewsArticle
import com.evothings.domain.util.DateFormat

internal fun ArticleResponse.toNewsArticle(): NewsArticle {
    val photoFallbackLink = "https://slabstore.ru/portfolio/13/4.jpg"
    val response = this
    return NewsArticle(
        id = response.id.toString(),
        title = response.name,
        publishingDate = response.publishedDate.tryFormatDate(inputFormat = DateFormat.ISO),
        categories = response.tags.joinToString(", ") { it.name },
        articleLink = response.url,
        content = response.content,
        previewImageLink = response.photo ?: photoFallbackLink
    )
}