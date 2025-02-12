package com.evothings.mhand.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.core.app.NotificationCompat
import com.evothings.domain.util.DateFormat.FULL_DATE_MICROSECONDS
import com.evothings.domain.feature.notification.model.Notification
import com.evothings.domain.feature.notification.model.NotificationType
import com.evothings.domain.feature.notification.repository.NotificationRepository
import com.evothings.mhand.MainActivity
import com.evothings.mhand.R
import com.evothings.mhand.presentation.utils.analytics.trackAnalyticsEvent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class PushNotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            notificationRepository.renewToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = message.data["id"]?.toInt() ?: -1

        notificationManager.createNotificationChannel(
            NotificationChannel("Notifications", "Notifications", NotificationManager.IMPORTANCE_HIGH)
        )

        val contentIntent = buildContentIntent(notificationId)

        if (message.data.isEmpty()) return

        writeNotificationToStorage(message)
        trackNotificationReceive(notificationId)

        val notification = buildNotificationObject(message, contentIntent)
        notificationManager.notify(Random.nextInt(until = 1298), notification)
    }

    private fun buildContentIntent(notificationId: Int): PendingIntent {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("notificationId", notificationId)
        }
        val combinedFlags = (PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        return PendingIntent.getActivity(this, notificationId, intent, combinedFlags)
    }

    private fun buildNotificationObject(
        message: RemoteMessage,
        contentIntent: PendingIntent
    ): android.app.Notification {
        return NotificationCompat.Builder(applicationContext, "Notifications")
            .setContentTitle(message.data.get("title") ?: "No title")
            .setContentText(message.data.get("body") ?: "No title")
            .setSmallIcon(R.drawable.app_icon)
            .setLargeIcon(Icon.createWithResource(applicationContext, R.drawable.app_icon))
            .setAutoCancel(true)
            .setContentIntent(contentIntent)
            .build()
    }

    private fun writeNotificationToStorage(
        message: RemoteMessage,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            notificationRepository.writeToCache(
                Notification(
                    type = message.data.get("type")?.resolveNotificationType() ?: NotificationType.INFORMATION,
                    title = message.data.get("title") ?: "No title",
                    description = message.data.get("body") ?: "No title",
                    arrivalTime = getCurrentTime(),
                    unread = true,
                )
            )
        }
    }

    private fun String.resolveNotificationType(): NotificationType = when(this) {
        "UPDATE" -> NotificationType.NEW_VERSION
        "INFO" -> NotificationType.INFORMATION
        "ALERT" -> NotificationType.ALERT
        else -> NotificationType.INFORMATION
    }

    private fun getCurrentTime(): String {
        val dateTimeFormat = SimpleDateFormat(FULL_DATE_MICROSECONDS, Locale.getDefault())
        return dateTimeFormat.format(Calendar.getInstance().time)
    }

    private fun trackNotificationReceive(id: Int) =
        trackAnalyticsEvent(
            name = "notification_receive",
            eventParams = mapOf("id" to "$id")
        )

}