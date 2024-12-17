package com.evothings.data.feature.news.dto

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    val id: Int,
    val name: String,
    @SerializedName("publish_at")
    val publishedDate: String,
    @SerializedName("image")
    val photo: String?,
    val tags: Array<ArticleTagResponse>,
    @SerializedName("share_link")
    val url: String,
    val content: String
)