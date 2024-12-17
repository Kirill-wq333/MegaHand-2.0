package com.evothings.data.feature.profile.dto.mapper

import com.evothings.data.feature.profile.dto.request.UpdateProfileRequest
import com.evothings.data.utils.date.tryFormatDate
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.util.DateFormat

internal fun Profile.toUpdateRequest(): UpdateProfileRequest {
    return UpdateProfileRequest(
        firstName = firstName,
        lastName = lastName,
        birthday = birthday.formatBirthdayOrNull(),
        city = city.ifEmpty { null },
        email = email.ifEmpty { null }
    )
}

private fun String.formatBirthdayOrNull(): String? {
    if (this.isEmpty()) return null
    return tryFormatDate(
        inputFormat = DateFormat.FULL_DATE,
        outputFormat = DateFormat.WESTERN_DATE
    )
}