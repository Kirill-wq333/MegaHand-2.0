package com.evothings.data.feature.notification.dto

import com.google.gson.annotations.SerializedName

data class NotificationsEnabledBody(
    @SerializedName("device_id") val deviceToken: String,
    @SerializedName("notification_available") val isEnabled: Boolean
)
