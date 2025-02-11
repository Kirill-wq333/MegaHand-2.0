package com.evothings.mhand.presentation.feature.cart.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.util.Mock
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.cart.ui.components.AuthorizationRequired
import com.evothings.mhand.presentation.feature.cart.ui.components.Checkout
import com.evothings.mhand.presentation.feature.cart.ui.components.productsList
import com.evothings.mhand.presentation.feature.cart.viewmodel.CartContract
import com.evothings.mhand.presentation.feature.cart.viewmodel.CartViewModel
import com.evothings.mhand.presentation.feature.onboarding.ui.screen.CartHeading
import com.evothings.mhand.presentation.feature.onboarding.ui.screen.CartOnboardingScreen
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.pullToRefresh.PullRefreshLayout
import com.evothings.mhand.presentation.feature.shared.screen.EmptyListScreen
import com.evothings.mhand.presentation.feature.shared.screen.ServerErrorScreen
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.list.toggleItem

private data class CartUiState(
    val products: List<Product>,
    val total: Double,
    val discount: Double,
    val cashbackPoints: Double,
    val summary: Double,
    val isAuthorized: Boolean
)

interface CartCallback {
    fun refreshScreen()
    fun openCatalog()
    fun onClickProduct(id: Int)
    fun removeFromCart(id: Int)
    fun toggleFavourite(id: Int)
    fun calculateCheckout(selection: List<Int>)
    fun proceedToCheckout(selection: List<Int>)
    fun openAuthScreen()
    fun finishOnboarding()
}

@Composable
fun CartScreen(
    vm: CartViewModel,
    openProductInfoScreen: (Int) -> Unit,
    openCheckoutScreen: (String) -> Unit,
    openCatalogScreen: () -> Unit,
    openAuthScreen: () -> Unit
){
    val context = LocalContext.current

    val state by vm.state.collectAsStateWithLifecycle()

    val products by vm.products.collectAsState()
    val total by vm.total.collectAsState()
    val discount by vm.discount.collectAsState()
    val cashbackPoints by vm.cashbackPoints.collectAsState()
    val summary by vm.summary.collectAsState()
    val isAuthorized = vm.isAuthorized

    LaunchedEffect(state) {
        if (state is CartContract.State.Loading) {
            vm.handleEvent(CartContract.Event.LoadCart)
        }
    }

    LaunchedEffect(vm.effect) {
        vm.effect.collect {
            when(it) {
                is CartContract.Effect.OpenCheckoutScreen -> openCheckoutScreen(it.orderId)
                is CartContract.Effect.ShowToast ->
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val callback = object : CartCallback {

        override fun onClickProduct(id: Int) =
            openProductInfoScreen(id)

        override fun removeFromCart(id: Int) =
            vm.handleEvent(CartContract.Event.DeleteFromCart(id))

        override fun proceedToCheckout(selection: List<Int>) =
            vm.handleEvent(CartContract.Event.CreateOrder(selection))

        override fun openCatalog() = openCatalogScreen()

        override fun openAuthScreen() = openAuthScreen()

        override fun refreshScreen() =
            vm.handleEvent(CartContract.Event.Refresh)

        override fun toggleFavourite(id: Int) =
            vm.handleEvent(CartContract.Event.ToggleFavourite(id))

        override fun calculateCheckout(selection: List<Int>) =
            vm.handleEvent(CartContract.Event.CalculateCheckout(selection))

        override fun finishOnboarding() =
            vm.handleEvent(CartContract.Event.FinishOnboarding)
    }

    if (state is CartContract.State.OnboardingActive) {
        CartOnboardingScreen(
            onFinish = callback::finishOnboarding
        )
    } else {
        CartContent(
            state = state,
            uiState = CartUiState(
                products = products,
                total = total,
                discount = discount,
                cashbackPoints = cashbackPoints,
                summary = summary,
                isAuthorized = isAuthorized.value
            ),
            callback = callback
        )
    }
}

@Composable
private fun CartContent(
    state: CartContract.State,
    uiState: CartUiState,
    callback: CartCallback
) {
    Scaffold(
        topBar = {
            CartHeading()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when(state) {
                is CartContract.State.Loading -> {
                    LoadingScreen()
                }
                is CartContract.State.Loaded -> {
                    PullRefreshLayout(onRefresh = callback::refreshScreen) {
                        Content(
                            products = uiState.products,
                            total = uiState.total,
                            discount = uiState.discount,
                            cashbackPoints = uiState.cashbackPoints,
                            summary = uiState.summary,
                            isAuthorized = uiState.isAuthorized,
                            callback = callback
                        )
                    }
                }
                is CartContract.State.NoItems -> {
                    EmptyListScreen(
                        text = stringResource(id = R.string.empty_cart_text),
                        onClickOpenCatalog = callback::openCatalog
                    )
                }
                is CartContract.State.ServerError -> {
                    ServerErrorScreen(
                        onRefresh = callback::refreshScreen
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun Content(
    products: List<Product>,
    total: Double,
    discount: Double,
    cashbackPoints: Double,
    summary: Double,
    isAuthorized: Boolean,
    callback: CartCallback
) {

    val availableProductIds = remember(products) {
        val inStockProducts = products.filter { it.availability == Product.Availability.IN_STOCK }
        inStockProducts.map { it.id }
    }

    val selectionList = remember {
        mutableStateListOf(*availableProductIds.toTypedArray())
    }

    val isSelectAll by rememberUpdatedState(
        newValue = selectionList.size == products.size
    )

    LaunchedEffect(selectionList.size) {
        callback.calculateCheckout(selectionList)
    }

    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        productsList(
            products = products,
            selectionList = selectionList,
            isSelectAll = isSelectAll,
            callback = callback,
            onSelect = { selectionList.toggleItem(it) },
            onSelectAll = {
                selectionList.clear()
                if (isSelectAll) return@productsList
                selectionList.addAll(availableProductIds)
            },
            onClearSelection = { selectionList.clear() }
        )

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.spacers.medium,
                        end = MaterialTheme.spacers.medium,
                        bottom = MaterialTheme.spacers.large
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isAuthorized) {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
                    Column {
                        Text(
                            text = stringResource(R.string.can_i_return_the_product),
                            color = colorScheme.secondary.copy(.4f),
                            style = MegahandTypography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
                        Text(
                            text = stringResource(R.string.how_the_delivery_takes_place),
                            color = colorScheme.secondary.copy(.4f),
                            style = MegahandTypography.bodyLarge                )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
                    CartCheckout(
                        productsCount = selectionList.size,
                        total = total,
                        discount = discount,
                        cashbackPoints = cashbackPoints,
                        summary = summary,
                        enableCheckoutButton = selectionList.isNotEmpty(),
                        onClickCheckout = {
                            callback.proceedToCheckout(selectionList)
                        }
                    )
                }else{
                    Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
                    Column {
                        Text(
                            text = stringResource(R.string.can_i_return_the_product),
                            color = colorScheme.secondary.copy(.4f),
                            style = MegahandTypography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
                        Text(
                            text = stringResource(R.string.how_the_delivery_takes_place),
                            color = colorScheme.secondary.copy(.4f),
                            style = MegahandTypography.bodyLarge                )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
                    AuthorizationRequired(
                        onClickLogin = callback::openAuthScreen
                    )
                }
            }
        }
    }
}

@Composable
fun CartCheckout(
    productsCount: Int,
    total: Double,
    discount: Double,
    cashbackPoints: Double,
    summary: Double,
    enableCheckoutButton: Boolean,
    onClickCheckout: () -> Unit
){
    Column {
        Checkout(
            productsCount = productsCount,
            total = total,
            discount = discount,
            cashbackPoints = cashbackPoints,
            summary = summary,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Button(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.proceed_checkout_button),
            isEnabled = enableCheckoutButton,
            backgroundColor = colorScheme.primary,
            textColor = ColorTokens.Graphite,
            onClick = onClickCheckout
        )
    }
}

@Preview
@Composable
private fun CartContentPreview() {

    MegahandTheme {
        CartContent(
            uiState = CartUiState(
                products = Mock.demoProductsList,
                total = 8.9,
                discount = 10.11,
                cashbackPoints = 12.13,
                summary = 14.15,
                isAuthorized = false,
            ),
            state = CartContract.State.Loaded,
            callback = object : CartCallback{
                override fun refreshScreen(){}
                override fun openCatalog(){}
                override fun onClickProduct(id: Int){}
                override fun removeFromCart(id: Int){}
                override fun toggleFavourite(id: Int){}
                override fun calculateCheckout(selection: List<Int>){}
                override fun proceedToCheckout(selection: List<Int>){}
                override fun openAuthScreen(){}
                override fun finishOnboarding(){}
            }
        )
    }

}