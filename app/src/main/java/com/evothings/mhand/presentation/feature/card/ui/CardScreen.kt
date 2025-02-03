package com.evothings.mhand.presentation.feature.card.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.card.model.Card
import com.evothings.domain.feature.card.model.Transaction
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.card.ui.components.CreditingAndDebiting
import com.evothings.mhand.presentation.feature.card.ui.components.HistoryBar
import com.evothings.mhand.presentation.feature.card.ui.components.bottomsheet.TransactionFilterPicker
import com.evothings.mhand.presentation.feature.card.ui.components.screen.LoyalityNotAvailableScreen
import com.evothings.mhand.presentation.feature.card.viewmodel.CardContract
import com.evothings.mhand.presentation.feature.card.viewmodel.CardViewModel
import com.evothings.mhand.presentation.feature.card.viewmodel.enumeration.CardFilterType
import com.evothings.mhand.presentation.feature.home.ui.LoyalityCard
import com.evothings.mhand.presentation.feature.onboarding.ui.screen.CardOnboardingScreen
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.header.ui.HeaderProvider
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.loyalityCard.BigQrcode
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.feature.shared.pullToRefresh.PullRefreshLayout
import com.evothings.mhand.presentation.feature.shared.screen.ServerErrorScreen
import com.evothings.mhand.presentation.feature.shared.screen.UserIsNotAuthorized
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.sdkutil.Connectivity

data class CardUiState(
    val card: Card = Card(),
    val cashback: Int = 0,
    val currentFilter: CardFilterType = CardFilterType.ALL,
    val transactions: Map<String, List<Transaction>> = mapOf(),
    val offlineMode: Boolean = false,
)

private interface CardScreenCallback {
    fun chooseFilter(filterIndex: Int) {}
    fun notifyLoyalitySystem() {}
    fun refresh() {}
    fun openProfileScreen() {}
}

@Stable
private object EmptyCardScreenCallback : CardScreenCallback

@Composable
fun CardScreen(
    vm: CardViewModel,
    openProfile: () -> Unit,
) {

    val context = LocalContext.current

    val cardUiState by vm.cardUiState.collectAsState()
    val state by vm.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        val isUserOffline = !Connectivity.hasInternetConnection(context)
        vm.handleEvent(CardContract.Event.LoadData(false, isUserOffline))
    }

    LaunchedEffect(vm.effect) {
        vm.effect.collect {
            when (it) {
                is CardContract.Effect.NavigateToProfile -> openProfile()
            }
        }
    }

    val callback = object : CardScreenCallback {

        override fun chooseFilter(filterIndex: Int) =
            vm.handleEvent(CardContract.Event.ChangeFilter(filterIndex))

        override fun refresh() {
            val isOffline = !Connectivity.hasInternetConnection(context)
            vm.handleEvent(CardContract.Event.LoadData(force = true, offlineMode = isOffline))
        }

        override fun notifyLoyalitySystem() {
            super.notifyLoyalitySystem()
        }

        override fun openProfileScreen() = openProfile()
    }

    if (state is CardContract.State.OnboardingActive) {
        CardOnboardingScreen(
            onFinish = { vm.handleEvent(CardContract.Event.FinishOnboarding) }
        )
        return
    }

    CardContent(
        state = state,
        uiState = cardUiState,
        callback = callback
    )
}

@Composable
private fun CardContent(
    state: CardContract.State,
    uiState: CardUiState,
    callback: CardScreenCallback
) {
    HeaderProvider(
        screenTitle = stringResource(id = R.string.card_screen_title),
        enableCardBalance = false,
        enableMapIconButton = false,
        onBack = {}
    ) { headerPadding ->
        PullRefreshLayout(
            modifier = Modifier.padding(headerPadding),
            onRefresh = callback::refresh
        ) {
            when (state) {
                is CardContract.State.Loading -> LoadingScreen()
                is CardContract.State.Loaded -> {
                    Content(
                        uiState = uiState,
                        callback = callback,
                        offlineMode = uiState.offlineMode
                    )
                }
                is CardContract.State.NetworkError -> {
                    ServerErrorScreen(
                        onRefresh = callback::refresh
                    )
                }
                is CardContract.State.UserIsNotAuthorized -> {
                    UserIsNotAuthorized(
                        onClickAuthorize = callback::openProfileScreen
                    )
                }
                is CardContract.State.LoyalityNotAvailable -> {
                    LoyalityNotAvailableScreen(
                        onNotify = {}
                    )
                }

                else -> {}
            }
        }
    }
}



@Composable
private fun Content(
    uiState: CardUiState,
    callback: CardScreenCallback = EmptyCardScreenCallback,
    offlineMode: Boolean
) {
    var qrViewIsVisible by remember { mutableStateOf(false) }

    var filterPickerBottomSheetExpanded by remember { mutableStateOf(false) }

    val transactionsListIsEmpty by remember {
        derivedStateOf {
            uiState.transactions.isEmpty() && uiState.currentFilter == CardFilterType.ALL
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            LoyalityCard(
                cashback = uiState.cashback,
                openProfile = callback::openProfileScreen,
                enableBalance = true,
                cardQRUrl = uiState.card.barcodeUrl.orEmpty(),
                showQR = { qrViewIsVisible = true },
                cardBalance = uiState.card.balance
            )
        }
        when {
            offlineMode -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        OfflineMode(
                            onReload = callback::refresh
                        )
                    }
                }
            }
            transactionsListIsEmpty -> {
                item { TransactionsListEmpty() }
            }
            else -> {
                history(
                    currentFilter = uiState.currentFilter,
                    transactions = uiState.transactions,
                    isFilterModalExpanded = filterPickerBottomSheetExpanded,
                    openFilterSelector = { filterPickerBottomSheetExpanded = true },
                )
            }
        }
    }

    AnimatedVisibility(
        visible = qrViewIsVisible,
        enter = fadeIn() + scaleIn(tween(150)),
        exit = fadeOut() + scaleOut(tween(150))
    ) {
        BigQrcode(
            qrCodeLink = uiState.card.barcodeUrl,
            onClose = { qrViewIsVisible = false }
        )
    }
    if (filterPickerBottomSheetExpanded) {
        MhandModalBottomSheet(
            onDismissRequest = { filterPickerBottomSheetExpanded = false }
        ) { hide ->
            TransactionFilterPicker(
                modifier = Modifier.modalBottomSheetPadding(),
                currentEntryIndex = uiState.currentFilter.ordinal,
                onDismiss = hide,
                onSelect = { index ->
                    callback.chooseFilter(index); hide()
                }
            )
        }
    }
}

@Composable
fun OfflineMode(
    modifier: Modifier = Modifier,
    onReload: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 95.dp,
                horizontal = MaterialTheme.paddings.extraGiant
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_card_offline),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Text(
            text = stringResource(R.string.card_offline_mode_title),
            color = colorScheme.secondary,
            style = MegahandTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Text(
            text = stringResource(R.string.card_offline_mode_subtitle),
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.reload_page),
            textColor = colorScheme.secondary,
            borderColor = colorScheme.secondary.copy(.1f),
            onClick = onReload,
        )
    }
}

private fun LazyListScope.history(
    currentFilter: CardFilterType,
    transactions: Map<String, List<Transaction>>,
    isFilterModalExpanded: Boolean,
    openFilterSelector: () -> Unit,
) {

    item {
        HistoryBar(
            isFilterPickerExpanded = isFilterModalExpanded,
            currentFilter = currentFilter,
            onExpand = openFilterSelector,
        )
    }

    items(transactions.keys.toList()) { key  ->
        val items = remember { transactions[key] } ?: return@items

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = MaterialTheme.spacers.medium
            )
        ) {
            Text(
                text = key,
                style = typography.labelLarge,
                color = colorScheme.secondary.copy(0.4f)
            )
            repeat(items.size) { i ->
                val item = remember { items[i] }
                CreditingAndDebiting(
                    date = item.date,
                    money = item.amount,
                    type = item.type
                )
            }
        }
    }
}

@Composable
private fun TransactionsListEmpty() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.transactions_list_is_empty),
                style = typography.headlineSmall
            )
            Text(
                text = stringResource(R.string.transactions_list_empty_subtitle),
                textAlign = TextAlign.Center,
                style = typography.labelLarge,
                color = colorScheme.secondary.copy(0.6f)
            )
        }
    }
}


@Preview
@Composable
private fun CardOffline() {
    MegahandTheme {
        Surface {
            OfflineMode { }
        }
    }
}

@Preview
@Composable
private fun CardPreview() {
    MegahandTheme(false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorScheme.onSecondary)
        ) {
            CardContent(
                state = CardContract.State.Loaded,
                uiState = CardUiState(
                    cashback = 3
                ),
                callback = object : CardScreenCallback{
                    override fun chooseFilter(filterIndex: Int) {}
                    override fun notifyLoyalitySystem() {}
                    override fun refresh() {}
                    override fun openProfileScreen() {}
                }
            )
        }
    }
}