package com.evothings.domain.feature.notification.repository

import com.evothings.domain.feature.notification.model.Notification

interface NotificationRepository {
    suspend fun subscribeToTopic(topicName: String, deviceToken: String)
    suspend fun incrementOpen(notificationId: Int)
    suspend fun writeToCache(notification: Notification)
    suspend fun renewToken(newToken: String)
    suspend fun updateNotificationsEnabledStatus(deviceToken: String, status: Boolean)
    suspend fun deleteById(notificationId: Int)
    suspend fun deleteAll()
    suspend fun getAll(): Result<List<Notification>>
    suspend fun markAllAsRead()
}