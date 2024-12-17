package com.evothings.domain.feature.news.repository

import com.evothings.domain.feature.news.model.NewsArticle
import com.evothings.domain.feature.news.model.NewsCategory

interface NewsRepository {
    suspend fun getNews(forceOnline: Boolean, tagId: Int?): Result<List<NewsArticle>>
    suspend fun getArticle(id: String): Result<NewsArticle>
    suspend fun getNewsTags(): Result<List<NewsCategory>>
}