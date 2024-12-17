package com.evothings.data.feature.header.dto.mapper

import com.evothings.data.feature.header.dto.CityDto
import com.evothings.domain.feature.home.model.CitiesMap
import com.evothings.domain.feature.home.model.City

internal fun Array<CityDto>.toCitiesMap(): CitiesMap {
    val result: MutableMap<String, List<City>> = mutableMapOf()
    val sortedList = this.sortedBy { it.city }

    sortedList.forEach {
        val capitalLetter = it.city.take(1)
        if (result[capitalLetter] == null) {
            val filteredList = sortedList
                .filter { it.city.startsWith(capitalLetter) }
                .map { dto ->
                    City(
                        id = dto.id,
                        name = dto.city,
                        loyalityAvailable = dto.loyalityExists,
                        chosen = false,
                    )
                }
            result[capitalLetter] = filteredList
        }
    }

    return result
}