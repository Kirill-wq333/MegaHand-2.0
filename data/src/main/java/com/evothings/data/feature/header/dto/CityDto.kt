package com.evothings.data.feature.header.dto

import com.google.gson.annotations.SerializedName

data class CityDto(
    val id: Int,
    val city: String,
    @SerializedName("application")
    val loyalityExists: Boolean
)