package com.evothings.data.feature.profile.dto.mapper

import com.evothings.data.feature.profile.dto.response.referals.ReferalsResponse
import com.evothings.data.utils.date.daysSinceDate
import com.evothings.domain.feature.profile.model.Referal
import com.evothings.domain.feature.profile.model.ReferalInfo

internal fun ReferalsResponse.toReferalInfo(): ReferalInfo = ReferalInfo(
    balance = this.referalBalance,
    referalsList = referrals.toList()
        .sortedByDescending { it.createdAt }
        .map {
            Referal(
                name = it.fullName,
                cashback = it.cashback,
                joinDate = calculateReferalJoinedSince(it.createdAt),
            )
        }
)

private fun calculateReferalJoinedSince(date: String): String {
    val daysCount = date.daysSinceDate()
    return if (daysCount == 0) "сегодня" else "$daysCount д. назад"
}