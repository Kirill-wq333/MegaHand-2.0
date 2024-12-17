package com.evothings.data.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal inline fun <reified T> T.toJson(): String = Json.encodeToString(this)

internal inline fun <reified T> String.fromJson(): T = Json.decodeFromString(this)