package com.evothings.data.feature.notification

import com.evothings.data.feature.notification.datasource.NotificationNetworkClient
import com.evothings.data.feature.notification.dto.DeviceIdBody
import com.evothings.data.feature.notification.dto.NotificationIdBody
import com.evothings.data.feature.notification.dto.NotificationsEnabledBody
import com.evothings.data.feature.notification.dto.TopicSubscriptionBody
import com.evothings.data.storage.room.dao.NotificationDao
import com.evothings.data.storage.room.entity.notification.mapListFromEntity
import com.evothings.data.storage.room.entity.notification.mapToEntity
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.auth.repository.AuthRepository
import com.evothings.domain.feature.notification.model.Notification
import com.evothings.domain.feature.notification.repository.NotificationRepository

class NotificationRepositoryImpl(
    private val notificationDao: NotificationDao,
    private val authRepository: AuthRepository,
    private val networkClient: NotificationNetworkClient
) : NotificationRepository {

    override suspend fun subscribeToTopic(topicName: String, deviceToken: String) {
        networkClient.subscribeToTopic(
            topicSubscriptionBody = TopicSubscriptionBody(
                topic = topicName,
                deviceId = deviceToken
            )
        ).awaitResult()
    }

    override suspend fun renewToken(newToken: String) {
        if (authRepository.isLoggedIn()) {
            networkClient.renewToken(DeviceIdBody(newToken)).awaitResult()
        } else {
            subscribeToTopic("main", newToken)
        }
    }

    override suspend fun updateNotificationsEnabledStatus(deviceToken: String, status: Boolean) {
        networkClient.updateNotificationsAvailability(
            NotificationsEnabledBody(deviceToken, status)
        ).awaitResult()
    }

    override suspend fun incrementOpen(notificationId: Int) {
        networkClient.incrementOpen(
            notificationId = NotificationIdBody(notificationId)
        ).awaitResult()
    }

    override suspend fun writeToCache(notification: Notification) =
        notificationDao.insertNotification(notification.mapToEntity())

    override suspend fun deleteById(notificationId: Int) =
        notificationDao.deleteNotificationById(notificationId)

    override suspend fun deleteAll() =
        notificationDao.deleteAllNotifications()

    override suspend fun getAll(): Result<List<Notification>> =
        runCatching {
            notificationDao.getAllNotifications().mapListFromEntity()
        }

    override suspend fun markAllAsRead() =
        notificationDao.setNotificationsAsRead()

}