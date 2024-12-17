package com.evothings.data.feature.news

import com.evothings.data.feature.news.datasource.NewsNetworkClient
import com.evothings.data.feature.news.dto.ArticleTagResponse
import com.evothings.data.feature.news.dto.mapper.toNewsArticle
import com.evothings.data.feature.news.dto.mapper.toNewsTagList
import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.cache.fetchCache
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.news.model.NewsArticle
import com.evothings.domain.feature.news.model.NewsCategory
import com.evothings.domain.feature.news.repository.NewsRepository

class NewsRepositoryImpl(
    private val networkClient: NewsNetworkClient,
    private val cacheStore: CacheStore
) : NewsRepository {

    override suspend fun getNews(forceOnline: Boolean, tagId: Int?): Result<List<NewsArticle>> =
        fetchCache(
            forceOnline = forceOnline,
            cacheKey = "news_$tagId",
            fetchFromNetwork = { networkClient.getNews(tagId).awaitResult() },
            cacheStore = cacheStore,
            mapper = {
                this.results.map { it.toNewsArticle() }
            }
        )

    override suspend fun getArticle(id: String): Result<NewsArticle> =
        fetchCache(
            forceOnline = false,
            cacheStore = cacheStore,
            cacheKey = "article_$id",
            fetchFromNetwork = { networkClient.getArticle(id).awaitResult() },
            mapper = { this.toNewsArticle() }
        )

    override suspend fun getNewsTags(): Result<List<NewsCategory>> =
        fetchCache(
            forceOnline = false,
            cacheStore = cacheStore,
            cacheKey = NetworkConfig.Routes.News.tags,
            fetchFromNetwork = { networkClient.getNewsTags().awaitResult() },
            mapper = Array<ArticleTagResponse>::toNewsTagList
        )

}







