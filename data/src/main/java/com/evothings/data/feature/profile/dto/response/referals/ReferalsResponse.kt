package com.evothings.data.feature.profile.dto.response.referals

data class ReferalsResponse(
    val referalBalance: Int,
    val referrals: Array<ReferralDto>
)