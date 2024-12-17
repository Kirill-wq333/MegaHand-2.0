package com.evothings.data.feature.news.datasource

import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.feature.news.dto.ArticleResponse
import com.evothings.data.feature.news.dto.ArticleTagResponse
import com.evothings.domain.feature.product.model.PagedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsNetworkClient {

    @GET(NetworkConfig.Routes.News.list)
    fun getNews(@Query("tag") tag: Int?): Call<PagedResponse<ArticleResponse>>

    @GET("${NetworkConfig.Routes.News.list}{id}")
    fun getArticle(@Path("id") id: String): Call<ArticleResponse>

    @GET(NetworkConfig.Routes.News.tags)
    fun getNewsTags(): Call<Array<ArticleTagResponse>>
}