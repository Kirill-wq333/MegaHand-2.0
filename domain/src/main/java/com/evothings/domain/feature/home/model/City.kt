package com.evothings.domain.feature.home.model

import kotlinx.serialization.Serializable

typealias CitiesMap = Map<String, List<City>>

@Serializable
data class City(
    val id: Int = -1,
    val name: String,
    var chosen: Boolean,
    val loyalityAvailable: Boolean
)