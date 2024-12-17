package com.evothings.data.feature.splash.dto

import com.evothings.domain.feature.splash.ApplicationStatus
import com.google.gson.annotations.SerializedName

data class SettingsDto(
    @SerializedName("technical_works")
    val technicalWorks: Boolean,
    @SerializedName("authorisation_technical_works")
    val authTechnicalWorks: Boolean
)

internal fun SettingsDto.map(): ApplicationStatus = ApplicationStatus(
    technicalWorks = this.technicalWorks,
    authTechnicalWorks = this.authTechnicalWorks
)