package com.evothings.mhand.presentation.feature.cart.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.cart.ui.components.Checkout
import com.evothings.mhand.presentation.feature.cart.viewmodel.CartContract
import com.evothings.mhand.presentation.feature.cart.viewmodel.CartViewModel
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

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
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = ColorTokens.Graphite,
            onClick = onClickCheckout
        )
    }
}