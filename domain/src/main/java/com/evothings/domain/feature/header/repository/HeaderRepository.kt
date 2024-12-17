package com.evothings.domain.feature.header.repository

import com.evothings.domain.feature.home.model.CitiesMap

interface HeaderRepository {
    suspend fun getCitiesList(): Result<CitiesMap>
}