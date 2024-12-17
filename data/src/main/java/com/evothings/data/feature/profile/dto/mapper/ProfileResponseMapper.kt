package com.evothings.data.feature.profile.dto.mapper

import com.evothings.data.feature.profile.dto.response.ProfileResponse
import com.evothings.data.utils.date.tryFormatDate
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.util.DateFormat

internal fun ProfileResponse.toProfile(): Profile {
    return Profile(
        firstName = firstName.orEmpty(),
        lastName = lastName.orEmpty(),
        birthday = birthday
            .orEmpty()
            .tryFormatDate(inputFormat = DateFormat.WESTERN_DATE),
        city = city.orEmpty(),
        email = email.orEmpty(),
        cashback = cashback,
        referalCode = referralCode,
        phoneNumber = formatPhone(phone),
    )
}

private fun formatPhone(phone: String): String {
    val phoneMask = "+# ### ### ##-##"
    var formatted = ""
    var indexInOriginal = 0
    for (char in phoneMask) {
        if (char != '#') {
            formatted += char
        } else {
            formatted += phone[indexInOriginal]
            indexInOriginal++
        }
    }
    return formatted
}