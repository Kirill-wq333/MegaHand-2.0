package com.evothings.domain.feature.home.repository

import com.evothings.domain.feature.home.model.Brand
import com.evothings.domain.feature.home.model.Story

interface HomeRepository {
    suspend fun getStories(): Result<List<Story>>
    suspend fun getBrands(): Result<List<Brand>>
    suspend fun submitUserSurvey(itemIndex: Int): Result<Unit>
    suspend fun transliterateCityName(city: String): Result<String>
}