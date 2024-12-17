package com.evothings.data.utils.date

import java.time.Instant
import java.time.ZoneId

fun expirationToTimestamp(date: String): Long {
    val instant = Instant.parse(date)
    val userZoneId = ZoneId.systemDefault()
    return instant.atZone(userZoneId).toInstant().toEpochMilli()
}