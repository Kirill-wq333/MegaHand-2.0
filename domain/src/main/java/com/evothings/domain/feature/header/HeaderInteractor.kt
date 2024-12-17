package com.evothings.domain.feature.header

import com.evothings.domain.feature.header.repository.HeaderRepository

class HeaderInteractor(private val repository: HeaderRepository) {

    suspend fun getCities() =
        repository.getCitiesList()

}