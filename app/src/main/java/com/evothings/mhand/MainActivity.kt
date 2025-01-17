package com.evothings.mhand

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.evothings.domain.feature.notification.repository.NotificationRepository
import com.evothings.mhand.presentation.NavigationHost
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.snackbar.host.SnackbarItemHost
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.viewmodel.ThemeViewModel
import com.evothings.mhand.presentation.utils.analytics.trackAnalyticsEvent
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var notificationRepository: NotificationRepository

    @Inject
    lateinit var snackbarItemHost: SnackbarItemHost

    private var navController: NavHostController? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        incrementNotificationOpenIfPresent()
        updateNotificationsEnabledStatus()

        setContent {
            navController = rememberNavController()

            val themeVm = hiltViewModel<ThemeViewModel>()

            val isDarkThemeEnabled by themeVm.isDarkThemeEnabled.collectAsState(initial = false)
            val snackbarItem by snackbarItemHost.currentSnackbarItem.collectAsState()

            MegahandTheme(isDarkThemeEnabled) {
                NavigationHost(
                    snackbarItem = snackbarItem,
                    navController = navController
                )
            }
        }

    }

    private fun incrementNotificationOpenIfPresent() {
        if (intent.extras == null) return
        lifecycleScope.launch(Dispatchers.IO) {
            val notificationId = intent.getIntExtra("notificationId", -1)
            trackNotificationOpen(notificationId)
            notificationRepository.incrementOpen(notificationId)
        }
    }

    private fun trackNotificationOpen(id: Int) =
        trackAnalyticsEvent(
            name = "notification_open",
            eventParams = mapOf("id" to "$id")
        )

    private fun updateNotificationsEnabledStatus() {
        val status = checkIsNotificationsEnabled()
        Firebase.messaging.token.addOnSuccessListener { deviceToken ->
            lifecycleScope.launch(Dispatchers.IO) {
                notificationRepository.updateNotificationsEnabledStatus(deviceToken, status)
            }
        }
    }

    private fun checkIsNotificationsEnabled(): Boolean {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.areNotificationsEnabled()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { safeIntent ->
            when {
                safeIntent.action == Intent.ACTION_VIEW -> {
                    handleWidgetDeeplinkIntent(safeIntent)
                }
            }
        }
    }

    private fun handleWidgetDeeplinkIntent(intent: Intent) {
        val intentData = intent.data ?: return
        val host = intentData.host

        val route =
            when(host) {
                CARD_DEEPLINK -> NavGraph.BottomNav.Catalog
                AUTH_DEEPLINK -> NavGraph.Auth.AuthenticationScreen
                else -> return
            }

        navController?.navigate(route)
    }

    companion object {
        const val CARD_DEEPLINK = "card"
        const val AUTH_DEEPLINK = "auth"
    }

}