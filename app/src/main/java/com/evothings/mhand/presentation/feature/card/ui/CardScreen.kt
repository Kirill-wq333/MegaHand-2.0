package com.evothings.mhand.presentation.feature.card.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.card.model.Card
import com.evothings.domain.feature.card.model.Transaction
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.card.ui.components.CreditingAndDebiting
import com.evothings.mhand.presentation.feature.card.ui.components.HistoryBar
import com.evothings.mhand.presentation.feature.card.viewmodel.CardContract
import com.evothings.mhand.presentation.feature.card.viewmodel.CardViewModel
import com.evothings.mhand.presentation.feature.card.viewmodel.enumeration.CardFilterType
import com.evothings.mhand.presentation.feature.home.ui.LoyalityCard
import com.evothings.mhand.presentation.feature.home.ui.components.CouponBanner
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.BottomBarNavigation
import com.evothings.mhand.presentation.feature.shared.header.Header
import com.evothings.mhand.presentation.theme.MegahandTheme
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

    var offlineMode by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        offlineMode = !Connectivity.hasInternetConnection(context)
        vm.handleEvent(CardContract.Event.LoadData(offlineMode))
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

        override fun refresh() = vm.handleEvent(CardContract.Event.ReloadPage)

        override fun notifyLoyalitySystem() {
            super.notifyLoyalitySystem()
        }

        override fun openProfileScreen() = openProfile()
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

    Content(
        uiState = uiState,
        callback = callback,
        offlineMode = uiState.offlineMode
    )
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

    val creditingAndDebitingList = listOf(
        CreditingAndDebitingClass(
            day = "Сегодня",
            date = "16:30",
            money = "200",
            color = colorScheme.inverseSurface.copy(.1f),
            colorIcon = colorScheme.inverseSurface,
            icon = ImageVector.vectorResource(R.drawable.ic_plus),
            selected = true
        ),
        CreditingAndDebitingClass(
            day = "25 ноября",
            date = "01:25",
            money = "500",
            color = colorScheme.error.copy(.1f),
            colorIcon = colorScheme.error,
            icon = ImageVector.vectorResource(R.drawable.ic_minus),
            selected = false
        )

    )


    Column(
        modifier = Modifier
            .padding(MaterialTheme.paddings.extraLarge)
    ) {
        LoyalityCard(
            cashback = uiState.cashback,
            openProfile = callback::openProfileScreen
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.mega))
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        HistoryBar()
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraLarge)
        ) {
            items(creditingAndDebitingList){ item ->
                CreditingAndDebiting(
                    day = item.day,
                    date = item.date,
                    money = item.money,
                    selected = item.selected,
                    color = item.color,
                    colorIcon = item.colorIcon,
                    icon = item.icon
                )
            }
        }
    }

}

data class CreditingAndDebitingClass(
    val day: String,
    val date: String,
    val money: String,
    val color: Color,
    val colorIcon: Color,
    val icon: ImageVector,
    val selected: Boolean
)

