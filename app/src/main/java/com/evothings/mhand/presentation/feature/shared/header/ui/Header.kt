package com.evothings.mhand.presentation.feature.shared.header.ui

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.evothings.domain.feature.notification.model.Notification
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.header.ui.components.BackButton
import com.evothings.mhand.presentation.feature.shared.header.ui.components.Logo
import com.evothings.mhand.presentation.feature.shared.header.ui.components.PrizeAndMoney
import com.evothings.mhand.presentation.feature.shared.header.ui.notifications.NotificationsTray
import com.evothings.mhand.presentation.feature.shared.header.viewmodel.HeaderContract
import com.evothings.mhand.presentation.feature.shared.header.viewmodel.HeaderViewModel
import com.evothings.mhand.presentation.feature.shared.header.viewmodel.base.BaseHeaderViewModel
import com.evothings.mhand.presentation.feature.shared.header.viewmodel.mock.MockHeaderViewModel
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.ChooseCityScreen
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.sdkutil.tryOpenWebPage


interface HeaderCallback {
    fun toggleDevMode()
    fun clearNotifications()
    fun deleteNotification(id: Int)
    fun onClickUpdateApp()
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    nameCategory: String,
    viewModel: BaseHeaderViewModel = createViewModel(),
    logoVisible: Boolean,
    balanceVisible: Boolean = true,
    notificationVisible: Boolean = true,
    locationVisible: Boolean = true,
    turnButtonVisible: Boolean = false,
    onBack: () -> Unit,
    onChooseCity: () -> Unit,
) {

    val context = LocalContext.current

    val cardBalance by viewModel.cardBalance.collectAsState()
    val notificationsList by viewModel.notifications.collectAsState()
    val unreadNotification by viewModel.unreadNotifications.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(HeaderContract.Event.GetNotifications)
        viewModel.handleEvent(HeaderContract.Event.GetCardBalance)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                is HeaderContract.Effect.ShowToast ->
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                else -> {}
            }
        }
    }

    val callback = object : HeaderCallback {

        override fun clearNotifications() =
            viewModel.handleEvent(HeaderContract.Event.DeleteAllNotifications)

        override fun deleteNotification(id: Int) =
            viewModel.handleEvent(HeaderContract.Event.DeleteNotification(id))

        override fun onClickUpdateApp() {
            val isHuaweiDevice = (Build.MANUFACTURER == "Huawei")
            val linkPrefix = if (isHuaweiDevice) "appmarket" else "market"
            tryOpenWebPage(context, "$linkPrefix://details?id=com.evothings.mhand")
        }

        override fun toggleDevMode() =
            viewModel.handleEvent(HeaderContract.Event.ToggleDevMode)

    }

    val displayCardBalance = remember(cardBalance,balanceVisible) {
        (cardBalance >= 0 && balanceVisible)
    }

    HeaderContent(
        modifier = modifier,
        nameCategory = nameCategory,
        logoVisible = logoVisible,
        balanceVisible = displayCardBalance,
        notificationVisible = notificationVisible,
        locationVisible = locationVisible,
        turnButtonVisible = turnButtonVisible,
        onBack = onBack,
        unreadNotifications = unreadNotification,
        onChooseCity = {
            viewModel.handleEvent(HeaderContract.Event.ChooseCity(it))
            onChooseCity()
        },
        readNotifications = {
            viewModel.handleEvent(HeaderContract.Event.ReadAllNotifications)
        },
        toggleDevMode = callback::toggleDevMode,
        cardBalance = cardBalance,
        callback = callback,
        notifications = notificationsList
    )
}


@Composable
private fun HeaderContent(
    modifier: Modifier = Modifier,
    nameCategory: String,
    notifications: List<Notification>,
    logoVisible: Boolean,
    balanceVisible: Boolean,
    notificationVisible: Boolean,
    locationVisible: Boolean,
    turnButtonVisible: Boolean,
    onBack: () -> Unit,
    onChooseCity: (String) -> Unit,
    readNotifications: () -> Unit,
    toggleDevMode: () -> Unit,
    callback: HeaderCallback,
    cardBalance: Int,
    unreadNotifications: Int
) {
    val context = LocalContext.current

    var notificationTrayVisible by remember { mutableStateOf(false) }
    var chooseCityScreenVisible by remember { mutableStateOf(false) }


    BackHandler(
        enabled = (notificationTrayVisible || chooseCityScreenVisible),
        onBack = {
            notificationTrayVisible = false
            chooseCityScreenVisible = false
        }
    )

    Column {
        Content(
            modifier = modifier,
            nameCategory = nameCategory,
            money = cardBalance,
            logoVisible = logoVisible,
            notificationVisible = notificationVisible,
            balanceVisible = balanceVisible,
            locationVisible = locationVisible,
            toggleDevMode = toggleDevMode,
            onBack = onBack,
            turnButtonVisible = turnButtonVisible,
            locationSheetOpen = chooseCityScreenVisible,
            notificationsSheetOpened = notificationTrayVisible,
            unreadNotifications = unreadNotifications,
            onClickLocation = {
                notificationTrayVisible = false
                chooseCityScreenVisible = !chooseCityScreenVisible
            },
            onClickNotification = {
                chooseCityScreenVisible = false
                notificationTrayVisible = !notificationTrayVisible
                readNotifications()
            }
        )

        if (chooseCityScreenVisible && !notificationTrayVisible) {
            ChooseCityScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.surface)
                    .padding(horizontal = 12.dp),
                onChoose = {
                    onChooseCity(it)
                    chooseCityScreenVisible = false
                }
            )
        }

        AnimatedVisibility(
            visible = notificationTrayVisible,
            enter = expandVertically(tween(700)),
            exit = shrinkVertically(tween(700))
        ) {
            NotificationsTray(
                list = notifications,
                onClickUpdate = callback::onClickUpdateApp,
                removeSingle = callback::deleteNotification,
                clearAll = callback::clearNotifications
            )
        }
    }

}

@Composable
private fun Content(
    money: Int,
    nameCategory: String,
    modifier: Modifier = Modifier,
    toggleDevMode: () -> Unit,
    logoVisible: Boolean,
    balanceVisible: Boolean,
    notificationVisible: Boolean,
    locationVisible: Boolean,
    turnButtonVisible: Boolean,
    onBack: () -> Unit,
    locationSheetOpen: Boolean,
    notificationsSheetOpened: Boolean,
    onClickLocation: () -> Unit,
    onClickNotification: () -> Unit,
    unreadNotifications: Int
) {
    val context = LocalContext.current

    var notificationTrayVisible by remember { mutableStateOf(false) }
    var chooseCityScreenVisible by remember { mutableStateOf(false) }

    val formattedTitle = remember(notificationTrayVisible, chooseCityScreenVisible, nameCategory) {
        when {
            notificationTrayVisible -> context.getString(R.string.notifications)
            chooseCityScreenVisible -> context.getString(R.string.city)
            else -> nameCategory
        }
    }

    val showLogo = remember(notificationTrayVisible, chooseCityScreenVisible) {
        logoVisible && !(notificationTrayVisible || chooseCityScreenVisible)
    }
    if (!locationVisible && !notificationVisible) return

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.extraLarge),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (showLogo) {
                Logo(
                    onLongClick = toggleDevMode
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
                ) {
                    if (turnButtonVisible) {
                        BackButton(onClick = onBack)
                    }
                    Text(
                        modifier = Modifier.basicMarquee(),
                        text = formattedTitle,
                        color = colorScheme.secondary,
                        style = MegahandTypography.headlineMedium
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(9.dp),
                    verticalAlignment = Alignment.CenterVertically
                    ) {
                AnimatedVisibility(
                    visible = balanceVisible,
                    enter = fadeIn(tween(300))
                ) {
                    PrizeAndMoney(
                        money = money,
                    )
                }
                if (locationVisible) {
                    IconButton(
                        icon = ImageVector.vectorResource(id = R.drawable.ic_location),
                        borderColor = if (locationSheetOpen) colorScheme.primary else Color.Transparent,
                        tint = colorScheme.secondary,
                        onClick = onClickLocation
                    )
                }
                if (notificationVisible) {
                    IconButton(
                        icon = ImageVector.vectorResource(id = R.drawable.ic_notifications),
                        tint = colorScheme.secondary,
                        borderColor = if (notificationsSheetOpened) colorScheme.primary else Color.Transparent,
                        badgeValue = unreadNotifications,
                        onClick = onClickNotification
                    )
                }
            }
        }
    }

}


@Composable
private fun createViewModel(): BaseHeaderViewModel =
    if (LocalInspectionMode.current)
        remember { MockHeaderViewModel() }
    else
        hiltViewModel<HeaderViewModel>()


@Preview
@Composable
fun PreviewHeader() {

    MegahandTheme {
        HeaderProvider(
            screenTitle = "Привет",
            isHomeScreen = true,
            onBack = {},
            onChooseCity = {},
            content = {  }
        )
    }

}

@Preview
@Composable
fun Preview(
    modifier: Modifier = Modifier
) {
  MegahandTheme { 
      Header(
          nameCategory = "False",
          onBack = {},
          onChooseCity = {},
          balanceVisible = true,
          locationVisible = true,
          notificationVisible = true,
          logoVisible = true
      )
  }
}