package com.evothings.data.feature.notification.dto

import com.google.gson.annotations.SerializedName

data class TopicSubscriptionBody(
    val topic: String,
    @SerializedName("device_id")
    val deviceId: String
)
