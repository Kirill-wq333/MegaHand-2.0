package com.evothings.data.feature.home.dto.mapper

import com.evothings.data.feature.home.dto.response.StoryResponse
import com.evothings.domain.feature.home.model.Story

internal fun Array<StoryResponse>.toStoriesList(): List<Story> = this.map { item ->
    with(item) {
        Story(
            title = title,
            description = description,
            imageLink = photo,
            articleLink = link,
            buttonLabel = buttonLabel
        )
    }
}