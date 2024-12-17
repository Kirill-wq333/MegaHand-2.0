package com.evothings.mhand.presentation.utils.sdkutil

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> String.fromJson(): T = Json.decodeFromString<T>(this)

inline fun <reified T> T.toJson(): String = Json.encodeToString(this)