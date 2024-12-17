package com.evothings.data.feature.notification.dto

import com.google.gson.annotations.SerializedName

data class DeviceIdBody(
    @SerializedName("device_id")
    val deviceId: String
)
