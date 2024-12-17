package com.evothings.data.feature.notification.datasource

import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.feature.notification.dto.DeviceIdBody
import com.evothings.data.feature.notification.dto.NotificationIdBody
import com.evothings.data.feature.notification.dto.NotificationsEnabledBody
import com.evothings.data.feature.notification.dto.TopicSubscriptionBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationNetworkClient {

    @POST(NetworkConfig.Routes.Notifications.subscribeToTopic)
    fun subscribeToTopic(@Body topicSubscriptionBody: TopicSubscriptionBody): Call<Unit>

    @POST(NetworkConfig.Routes.Notifications.renewToken)
    fun renewToken(@Body deviceIdBody: DeviceIdBody): Call<Unit>

    @POST(NetworkConfig.Routes.Notifications.incrementOpen)
    fun incrementOpen(@Body notificationId: NotificationIdBody): Call<Unit>

    @POST(NetworkConfig.Routes.Notifications.addAvailableStatus)
    fun updateNotificationsAvailability(@Body payload: NotificationsEnabledBody): Call<Unit>

}