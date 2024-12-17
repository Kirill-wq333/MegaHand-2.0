package com.evothings.domain.feature.news.interactor

import com.evothings.domain.feature.news.model.NewsArticle
import com.evothings.domain.feature.news.repository.NewsRepository

class NewsInteractor(private val newsRepository: NewsRepository) {

    suspend fun getNews(forceOnline: Boolean = false, tagId: Int?): Result<List<NewsArticle>> =
        newsRepository.getNews(forceOnline, tagId)

    suspend fun getNewsCategories() = newsRepository.getNewsTags()

    suspend fun getArticle(id: String) =
        newsRepository.getArticle(id)

}