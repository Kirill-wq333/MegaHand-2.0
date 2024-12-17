package com.evothings.data.feature.news.dto.mapper

import com.evothings.data.feature.news.dto.ArticleTagResponse
import com.evothings.domain.feature.news.model.NewsCategory

internal fun Array<ArticleTagResponse>.toNewsTagList(): List<NewsCategory> {
    return this.map { NewsCategory(id = it.id, name = it.name) }
}