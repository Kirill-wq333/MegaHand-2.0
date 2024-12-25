package com.evothings.mhand.presentation.feature.shared.header.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.header.ui.components.BackButton
import com.evothings.mhand.presentation.feature.shared.header.ui.components.Logo
import com.evothings.mhand.presentation.feature.shared.header.ui.components.PrizeAndMoney
import com.evothings.mhand.presentation.feature.shared.header.view.base.BaseHeaderViewModel
import com.evothings.mhand.presentation.feature.shared.header.view.mock.MockHeaderViewModel
import com.evothings.mhand.presentation.feature.shared.header.viewmodel.HeaderContract
import com.evothings.mhand.presentation.feature.shared.header.viewmodel.HeaderViewModel
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
    logoVisible: Boolean = false,
    balanceVisible: Boolean = true,
    notificationVisible: Boolean = true,
    locationVisible: Boolean = true,
    chevronLeftVisible: Boolean = false
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
            val isHuaweiDevice = (android.os.Build.MANUFACTURER == "Huawei")
            val linkPrefix = if (isHuaweiDevice) "appmarket" else "market"
            tryOpenWebPage(context, "$linkPrefix://details?id=com.evothings.mhand")
        }

        override fun toggleDevMode() =
            viewModel.handleEvent(HeaderContract.Event.ToggleDevMode)

    }

    val displayCardBalance = remember(cardBalance,balanceVisible) {
        (cardBalance >= 0 && balanceVisible)
    }

   Content(
       nameCategory = nameCategory,
       money = cardBalance,
       logoVisible = logoVisible,
       notificationVisible = notificationVisible,
       balanceVisible = displayCardBalance,
       chevronLeftVisible = chevronLeftVisible,
       locationVisible = locationVisible,
   )
}

@Composable
private fun Content(
    money: Int,
    nameCategory: String,
    modifier: Modifier = Modifier,
    logoVisible: Boolean,
    balanceVisible: Boolean,
    notificationVisible: Boolean,
    locationVisible: Boolean,
    chevronLeftVisible: Boolean,
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



    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.paddings.extraLarge),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Logo(
            visible = showLogo
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (chevronLeftVisible) {
                BackButton()
                Spacer(modifier = modifier.width(MaterialTheme.spacers.medium))
            }
            Text(
                text = formattedTitle,
                color = colorScheme.secondary,
                style = MegahandTypography.headlineMedium
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            PrizeAndMoney(
                money = "$money ₽",
                selected = balanceVisible
            )
            Spacer(modifier = modifier.width(MaterialTheme.spacers.normal))
            IconNavigation(
                imageVector = ImageVector.vectorResource(R.drawable.ic_location),
                contentDescription = "location",
                visible = locationVisible
            )
            IconNavigation(
                imageVector = ImageVector.vectorResource(R.drawable.ic_notifications),
                contentDescription = "notification",
                visible = notificationVisible
            )
        }
    }

}


@Composable
fun IconNavigation(
    imageVector: ImageVector,
    contentDescription: String?,
    visible: Boolean
) {
    if (visible) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = colorScheme.secondary,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.large)
            )
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
fun PreviewHeader(){
    MegahandTheme {
        Surface(color = colorScheme.secondary) {
            Header(
                nameCategory = "Магазины",
                chevronLeftVisible = true,
                logoVisible = false,
                notificationVisible = true,
                locationVisible = true,
                balanceVisible = false
            )
        }
    }
}
