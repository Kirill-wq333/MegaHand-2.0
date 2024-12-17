package com.evothings.domain.feature.home.model

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val title: String,
    val imageLink: String,
    val description: String,
    val buttonLabel: String,
    val articleLink: String
)