package com.evothings.data.storage.room.entity.notification

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.evothings.domain.feature.notification.model.Notification
import com.evothings.domain.feature.notification.model.NotificationType

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: Int,
    val title: String,
    val description: String,
    val arrivalTime: String,
    val unread: Boolean
)

fun Notification.mapToEntity(): NotificationEntity = NotificationEntity(
    type = this.type.convertToInt(),
    title = this.title,
    description = this.description,
    arrivalTime = this.arrivalTime,
    unread = this.unread
)

fun List<NotificationEntity>.mapListFromEntity(): List<Notification> = this.map {
    Notification(
        id = it.id,
        type = it.type.toNotificationType(),
        title = it.title,
        description = it.description,
        arrivalTime = it.arrivalTime,
        unread = it.unread
    )
}

internal fun NotificationType.convertToInt(): Int = when(this) {
    NotificationType.NEW_VERSION -> 0
    NotificationType.INFORMATION -> 1
    NotificationType.ALERT -> 2
    else -> 1
}

internal fun Int.toNotificationType(): NotificationType = when(this) {
    0 -> NotificationType.NEW_VERSION
    1 -> NotificationType.INFORMATION
    2 -> NotificationType.ALERT
    else -> NotificationType.INFORMATION
}