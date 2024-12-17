package com.evothings.domain.feature.notification.model

data class Notification(
    val id: Int = 0,
    val type: NotificationType,
    val title: String,
    val description: String,
    val arrivalTime: String,
    var unread: Boolean
)