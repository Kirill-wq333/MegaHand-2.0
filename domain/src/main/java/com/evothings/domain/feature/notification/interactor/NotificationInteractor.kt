package com.evothings.domain.feature.notification.interactor

import com.evothings.domain.feature.notification.repository.NotificationRepository

class NotificationInteractor(private val repository: NotificationRepository) {

    suspend fun deleteNotificationById(id: Int) =
        repository.deleteById(id)

    suspend fun updateToken(newToken: String) =
        repository.renewToken(newToken)

    suspend fun subscribeToTopic(topicName: String, deviceToken: String) =
        repository.subscribeToTopic(topicName, deviceToken)

    suspend fun deleteAllNotifications() =
        repository.deleteAll()

    suspend fun getAllNotifications() =
        repository.getAll()

    suspend fun setAllNotificationsAsRead() =
        repository.markAllAsRead()

}