package com.evothings.data.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.evothings.data.storage.room.entity.notification.NotificationEntity

@Dao
interface NotificationDao {

    @Insert(entity = NotificationEntity::class)
    suspend fun insertNotification(notificationEntity: NotificationEntity)

    @Query("DELETE FROM notifications WHERE id = :id")
    suspend fun deleteNotificationById(id: Int)

    @Query("DELETE FROM notifications")
    suspend fun deleteAllNotifications()

    @Query("SELECT * FROM notifications")
    suspend fun getAllNotifications(): List<NotificationEntity>

    @Query("UPDATE notifications SET unread = 0")
    suspend fun setNotificationsAsRead()

}