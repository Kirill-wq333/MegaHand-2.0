package com.evothings.data.feature.home.dto.response

import com.google.gson.annotations.SerializedName

data class StoryResponse(
    val photo: String,
    val title: String,
    val description: String,
    val link: String,
    @SerializedName("button_text")
    val buttonLabel: String
)
