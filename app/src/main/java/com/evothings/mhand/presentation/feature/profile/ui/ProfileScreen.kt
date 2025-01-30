package com.evothings.mhand.presentation.feature.profile.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.profile.model.Order
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.feature.profile.model.ReferalInfo
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.mock.MockNavigationBar
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.onboarding.ui.screen.ProfileOnboarding
import com.evothings.mhand.presentation.feature.payment.PaymentActivity
import com.evothings.mhand.presentation.feature.payment.launcher.rememberPaymentActivityLauncher
import com.evothings.mhand.presentation.feature.profile.ui.state.data.ProfileDataScreen
import com.evothings.mhand.presentation.feature.profile.ui.state.ordersHistory.OrdersHistoryContent
import com.evothings.mhand.presentation.feature.profile.ui.state.requiredFields.RequiredFieldsScreen
import com.evothings.mhand.presentation.feature.profile.viewmodel.ProfileContract
import com.evothings.mhand.presentation.feature.profile.viewmodel.ProfileViewModel
import com.evothings.mhand.presentation.feature.shared.button.Chip
import com.evothings.mhand.presentation.feature.shared.header.ui.HeaderProvider
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.pullToRefresh.PullRefreshLayout
import com.evothings.mhand.presentation.feature.shared.screen.EmptyListScreen
import com.evothings.mhand.presentation.feature.shared.screen.LoadingTechnicalServiceScreen
import com.evothings.mhand.presentation.feature.shared.screen.ServerErrorScreen
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.sdkutil.Connectivity
import com.evothings.mhand.presentation.utils.widget.sendUpdateWidgetBroadcast

private data class ProfileUiState(
    val profile: Profile = Profile(),
    val addresses: List<Address> = listOf(),
    val referalInfo: ReferalInfo = ReferalInfo(),
    val orders: List<Order> = listOf(),
    val query: String = "",
    val dateQuery: String = "",
)

interface ProfileCallback {
    fun reload() {}
    fun updateProfile(profile: Profile) {}
    fun confirmPhone(newPhone: String) {}
    fun logout() {}
    fun deleteAccount() {}
    fun searchOrders(query: String) {}
    fun getOrdersByDate(date: String) {}
    fun continueOrderCheckout(orderId: String) {}
    fun payForOrder(paymentLink: String?) {}
    fun openProductInfoScreen(id: Int) {}
    fun copyOrderTrack(track: String) {}
    fun openAddressMap(city: String) {}
    fun openCatalogScreen() {}
    fun toggleContentState() {}
    fun disableOnboarding() {}
}

@Stable
private object EmptyProfileCallback : ProfileCallback

@Composable
fun ProfileScreen(
    vm: ProfileViewModel,
    openAuth: () -> Unit,
    openConfirmPhoneScreen: (String) -> Unit,
    openProductInfoScreen: (Int) -> Unit,
    openCatalog: () -> Unit,
    openAddressMap: (String) -> Unit,
    openCheckoutScreen: (orderId: String) -> Unit
) {

    val context = LocalContext.current

    val state by vm.state.collectAsStateWithLifecycle()

    val profile by vm.profile.collectAsState()
    val addresses by vm.addresses.collectAsState()
    val referalInfo by vm.referralInfo.collectAsState()

    val orders by vm.orders.collectAsState()
    val query by vm.ordersQuery.collectAsState()
    val dateQuery by vm.ordersDateQuery.collectAsState()

    LaunchedEffect(state) {
        if (state is ProfileContract.State.Loading) {
            vm.handleEvent(ProfileContract.Event.LoadData)
        }
    }

    LaunchedEffect(vm.effect) {
        vm.effect.collect { effect ->
            when(effect) {
                is ProfileContract.Effect.NavigateToCode -> openConfirmPhoneScreen(effect.newPhone)
                is ProfileContract.Effect.NavigateToAuth -> {
                    if (Connectivity.hasInternetConnection(context)) {
                        openAuth()
                    } else {
                        vm.setNetworkIsUnavailableState()
                    }
                }
                is ProfileContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is ProfileContract.Effect.UpdateWidget -> sendUpdateWidgetBroadcast(context)
            }
        }
    }

    val paymentActivityLauncher = rememberPaymentActivityLauncher(
        onPaymentFinished = { success ->
            vm.handleEvent(
                ProfileContract.Event.HandleOrderPaymentStatus(success)
            )
        }
    )

    val callback = object : ProfileCallback {

        override fun reload() =
            vm.handleEvent(ProfileContract.Event.Refresh)

        override fun updateProfile(profile: Profile) =
            vm.handleEvent(ProfileContract.Event.SaveProfileChanges(profile))

        override fun confirmPhone(newPhone: String) =
            vm.handleEvent(ProfileContract.Event.ConfirmPhone(newPhone))

        override fun deleteAccount() =
            vm.handleEvent(ProfileContract.Event.DeleteAccount)

        override fun searchOrders(query: String) =
            vm.handleEvent(ProfileContract.Event.SearchOrders(query))

        override fun getOrdersByDate(date: String) =
            vm.handleEvent(ProfileContract.Event.SearchByDate(date))

        override fun continueOrderCheckout(orderId: String) =
            openCheckoutScreen(orderId)

        override fun payForOrder(paymentLink: String?) {
            paymentLink?.let { link ->
                val paymentIntent = Intent(context, PaymentActivity::class.java)
                paymentIntent.putExtra(PaymentActivity.PAYMENT_LINK_EXTRA, link)
                paymentActivityLauncher.launch(paymentIntent)
            }
        }

        override fun openProductInfoScreen(id: Int) =
            openProductInfoScreen(id)

        override fun openAddressMap(city: String) = openAddressMap(city)

        override fun openCatalogScreen() = openCatalog()

        override fun copyOrderTrack(track: String) {
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val clip = ClipData.newPlainText(context.getString(R.string.order_track), track)
            clipboardManager.setPrimaryClip(clip)

            Toast.makeText(
                context,
                context.getString(R.string.order_track_copy_success),
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun logout() {
            vm.handleEvent(ProfileContract.Event.Logout)
            sendUpdateWidgetBroadcast(context)
            openAuth()
        }

        override fun toggleContentState() =
            vm.handleEvent(ProfileContract.Event.ToggleContentState)

        override fun disableOnboarding() =
            vm.handleEvent(ProfileContract.Event.FinishOnboarding)
    }

    if (state is ProfileContract.State.OnboardingActive) {
        ProfileOnboarding(
            onFinish = callback::disableOnboarding
        )
        return
    }

    ProfileScreenContent(
        state = state,
        uiState = ProfileUiState(
            profile = profile,
            addresses = addresses,
            referalInfo = referalInfo,
            orders = orders,
            query = query,
            dateQuery = dateQuery,
        ),
        callback = callback
    )

}

@Composable
private fun ProfileScreenContent(
    state: ProfileContract.State,
    uiState: ProfileUiState,
    callback: ProfileCallback
) {

    HeaderProvider(
        screenTitle = stringResource(id = R.string.profile_screen_title),
        enableMapIconButton = false,
        turnButtonVisible = false,
        onBack = {}
    ) { headerPadding ->

        when(state) {
            is ProfileContract.State.Loading -> LoadingScreen()

            is ProfileContract.State.UserData,
            is ProfileContract.State.EmptyOrdersHistory,
            is ProfileContract.State.OrdersHistory -> {
                PullRefreshLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(headerPadding),
                    onRefresh = callback::reload
                ) {
                    Content(
                        state = state,
                        uiState = uiState,
                        callback = callback
                    )
                }
            }

            is ProfileContract.State.NecessaryFieldsUnfilled -> {
                RequiredFieldsScreen(
                    modifier = Modifier
                        .padding(headerPadding),
                    model = uiState.profile,
                    onSave = { profile ->
                        callback.updateProfile(profile)
                    }
                )
            }
            is ProfileContract.State.ServerError -> {
                ServerErrorScreen(
                    onRefresh = callback::reload
                )
            }

            is ProfileContract.State.AuthorizationTechnicalWorks -> LoadingTechnicalServiceScreen()

            else -> {}
        }

    }

}

@Composable
private fun Content(
    state: ProfileContract.State,
    uiState: ProfileUiState,
    callback: ProfileCallback
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacers.large)
        ) {
            ProfileContentSelector(
                state = state,
                toggleState = callback::toggleContentState
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.large)
            )
            when(state) {
                is ProfileContract.State.UserData -> {
                    ProfileDataScreen(
                        profile = uiState.profile,
                        addresses = uiState.addresses,
                        referalInfo = uiState.referalInfo,
                        callback = callback
                    )
                }

                is ProfileContract.State.OrdersHistory -> {
                    OrdersHistoryContent(
                        orders = uiState.orders,
                        query = uiState.query,
                        dateFilter = uiState.dateQuery,
                        onChangeSearchQuery = callback::searchOrders,
                        onChangeDateFilter = callback::getOrdersByDate,
                        onContinueCheckout = callback::continueOrderCheckout,
                        onClickPayOrder = callback::payForOrder,
                        onClickProduct = callback::openProductInfoScreen,
                        onCopyOrderTrack = callback::copyOrderTrack,
                    )
                }

                is ProfileContract.State.EmptyOrdersHistory -> {
                    EmptyListScreen(
                        text = stringResource(R.string.empty_order_history),
                        onClickOpenCatalog = callback::openCatalogScreen
                    )
                }

                else -> {}
            }
        }

    }

}

@Composable
fun ProfileContentSelector(
    state: ProfileContract.State,
    toggleState: () -> Unit
) {

    val ordersHistoryChipEnabled = remember(state) {
        state is ProfileContract.State.OrdersHistory || state is ProfileContract.State.EmptyOrdersHistory
    }

    Row {
        Chip(
            text = stringResource(R.string.user_data_chip),
            enabled = (state is ProfileContract.State.UserData),
            onClick = toggleState
        )
        Spacer(
            modifier = Modifier
                .width(MaterialTheme.spacers.small)
        )
        Chip(
            text = stringResource(R.string.orders_history_chip),
            enabled = ordersHistoryChipEnabled,
            onClick = toggleState
        )
    }
}

@Preview(showSystemUi = true, fontScale = 1.65f)
@Preview(showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MegahandTheme {
        Scaffold(
            bottomBar = { MockNavigationBar(NavGraph.BottomNav.ShoppingCart) }
        ) {
            Box(
                modifier = Modifier.padding(it)
            ) {
                ProfileScreenContent(
                    state = ProfileContract.State.UserData,
                    uiState = ProfileUiState(),
                    callback = EmptyProfileCallback
                )
            }
        }
    }
}