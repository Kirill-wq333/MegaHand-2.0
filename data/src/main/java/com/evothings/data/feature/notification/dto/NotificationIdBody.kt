package com.evothings.data.feature.notification.dto

import com.google.gson.annotations.SerializedName

data class NotificationIdBody(
    @SerializedName("notification_id")
    val notificationId: Int
)
