package com.evothings.mhand.presentation.feature.checkout.ui

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.checkout.model.CheckoutResult
import com.evothings.domain.feature.checkout.model.DeliveryOption
import com.evothings.domain.feature.checkout.model.PickupPoint
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.auth.ui.components.PrivacyPolicyText
import com.evothings.mhand.presentation.feature.cart.ui.components.Checkout
import com.evothings.mhand.presentation.feature.checkout.ui.components.PersonalData
import com.evothings.mhand.presentation.feature.checkout.ui.components.ReceiptMethodAndAddress
import com.evothings.mhand.presentation.feature.checkout.ui.components.SystemLoyality
import com.evothings.mhand.presentation.feature.checkout.ui.components.delivery.MapViewScreen
import com.evothings.mhand.presentation.feature.checkout.ui.model.CheckoutUiState
import com.evothings.mhand.presentation.feature.checkout.ui.tool.CheckoutFieldsValidator.validateAddress
import com.evothings.mhand.presentation.feature.checkout.ui.tool.CheckoutFieldsValidator.validatePersonal
import com.evothings.mhand.presentation.feature.checkout.viewmodel.CheckoutContract
import com.evothings.mhand.presentation.feature.checkout.viewmodel.CheckoutViewModel
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Information
import com.evothings.mhand.presentation.feature.payment.PaymentActivity
import com.evothings.mhand.presentation.feature.payment.launcher.rememberPaymentActivityLauncher
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.header.ui.HeaderProvider
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.product.components.ProductPhoto
import com.evothings.mhand.presentation.feature.shared.screen.ServerErrorScreen
import com.evothings.mhand.presentation.feature.shared.text.saver.AnySaver
import com.evothings.mhand.presentation.feature.shared.text.saver.BooleanSaver
import com.evothings.mhand.presentation.feature.shared.text.saver.IntSaver
import com.evothings.mhand.presentation.feature.shared.text.saver.StringSaver
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.sdkutil.openPrivacyPolicyPage

private interface CheckoutCallback {
    fun onBack()
    fun refreshScreen()
    fun openPrivacyPolicy()
    fun calculateDeliveryCost(
        address: String,
        deliveryOption: DeliveryOption
    )
    fun saveAddress(address: Address)
    fun calculatePointsDiscount(pointsAmount: Int)
    fun onCheckout(result: CheckoutResult)
    fun onClickProduct(id: Int)
    fun onChangePickupCity(city: String)
    fun openAddressMap(city: String)
    fun updateAddressesList()
}


@Composable
fun MakingAnOrderScreen(
    modifier: Modifier = Modifier,
    vm: CheckoutViewModel,
    orderId: String,
    openProductInfoScreen: (Int) -> Unit,
    openAddressMap: (String) -> Unit,
    openProfile: () -> Unit,
    onBack: () -> Unit
){

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val state by vm.state.collectAsStateWithLifecycle()
    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(Unit) {
        vm.handleEvent(CheckoutContract.Event.LoadCheckoutInfo(orderId))
    }

    val paymentActivityLauncher = rememberPaymentActivityLauncher(
        onPaymentFinished = { status ->
            vm.handleEvent(CheckoutContract.Event.HandlePaymentResult(status))
        }
    )

    LaunchedEffect(vm.effect) {
        vm.effect.collect { effect ->
            when(effect) {
                is CheckoutContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is CheckoutContract.Effect.OpenProfileScreen -> openProfile()
                is CheckoutContract.Effect.OpenPaymentActivity -> {
                    val intent = Intent(context, PaymentActivity::class.java)
                    intent.putExtra(PaymentActivity.PAYMENT_LINK_EXTRA, effect.paymentLink)
                    paymentActivityLauncher.launch(intent)
                }
            }
        }
    }

    val callback = object : CheckoutCallback {

        override fun onBack() = onBack()

        override fun refreshScreen() =
            vm.handleEvent(CheckoutContract.Event.Refresh(orderId))

        override fun openPrivacyPolicy() {
            openPrivacyPolicyPage(context)
        }

        override fun saveAddress(address: Address) {
            vm.handleEvent(CheckoutContract.Event.SaveAddress(address))
        }

        override fun onCheckout(result: CheckoutResult) {
            focusManager.clearFocus()
            vm.handleEvent(CheckoutContract.Event.Checkout(orderId, result))
        }

        override fun updateAddressesList() =
            vm.handleEvent(CheckoutContract.Event.UpdateAddressesList)

        override fun onChangePickupCity(city: String) {
            focusManager.clearFocus()
            vm.handleEvent(CheckoutContract.Event.LoadPickupPoints(city))
        }

        override fun calculateDeliveryCost(address: String, deliveryOption: DeliveryOption) {
            vm.handleEvent(
                CheckoutContract.Event.CalculateDeliveryCost(orderId, address, deliveryOption)
            )
        }

        override fun calculatePointsDiscount(pointsAmount: Int) {
            vm.handleEvent(
                CheckoutContract.Event.CalculatePointsDiscount(orderId, pointsAmount)
            )
        }

        override fun openAddressMap(city: String) = openAddressMap(city)

        override fun onClickProduct(id: Int) =
            openProductInfoScreen(id)
    }

    CheckoutContent(
        state = state,
        uiState = uiState,
        callback = callback
    )
}


@Composable
private fun CheckoutContent(
    state: CheckoutContract.State,
    uiState: CheckoutUiState,
    callback: CheckoutCallback
) {

    HeaderProvider(
        screenTitle = stringResource(R.string.checkout_title),
        turnButtonVisible = true,
        enableMapIconButton = false,
        enableCardBalance = false,
        enableNotificationButton = false,
        onBack = callback::onBack
    ) { headerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(headerPadding)
        ) {
            when (state) {
                is CheckoutContract.State.Loading -> {
                    LoadingScreen()
                }

                is CheckoutContract.State.Loaded -> {
                    Content(
                        uiState = uiState,
                        callback = callback,
                        openAddressMap = callback::openAddressMap,
                    )
                }

                is CheckoutContract.State.ServerError -> {
                    ServerErrorScreen(
                        onRefresh = callback::refreshScreen
                    )
                }
            }

        }

    }

}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    uiState: CheckoutUiState,
    callback: CheckoutCallback,
    openAddressMap: (String) -> Unit,
    ) {
    // Personal
    var name by rememberSaveable(uiState.profile.firstName, StringSaver) { mutableStateOf(uiState.profile.firstName) }
    var surname by rememberSaveable(uiState.profile.lastName, StringSaver) { mutableStateOf(uiState.profile.lastName) }
    var phone by rememberSaveable(uiState.profile.phoneNumber, StringSaver) {
        val cleanNumber = uiState.profile.phoneNumber.filter { it.isDigit() }
        mutableStateOf(cleanNumber)
    }
    var email by rememberSaveable(uiState.profile.email, StringSaver) { mutableStateOf(uiState.profile.email) }
    var saveInProfileCheckboxState by rememberSaveable(Unit, BooleanSaver) {
        mutableStateOf(false)
    }

    // Delivery
    var selectedDeliveryOption by rememberSaveable(Unit, AnySaver) { mutableStateOf(DeliveryOption.COURIER) }
    var selectedAddress by rememberSaveable(Unit, IntSaver) {
        val primaryAddressIndex = uiState.addresses
            .indexOfFirst { it.main }
            .coerceAtLeast(0)
        mutableIntStateOf(primaryAddressIndex)
    }
    var showCDEKMap by remember { mutableStateOf(false) }
    var saveNewAddress by rememberSaveable(Unit, BooleanSaver) { mutableStateOf(true) }
    var newAddress by remember { mutableStateOf(Address()) }
    var selectedPickupPoint by remember(uiState.pickupPoints) {
        val initialValue = uiState.pickupPoints.firstOrNull() ?: PickupPoint()
        mutableStateOf(initialValue)
    }

    LaunchedEffect(newAddress, selectedAddress, uiState.pickupCity) {
        val deliveryCity = if (selectedDeliveryOption == DeliveryOption.COURIER) {
            if (uiState.addresses.isNotEmpty()) {
                uiState.addresses[selectedAddress].city
            } else {
                newAddress.city
            }
        } else uiState.pickupCity

        if (deliveryCity.isNotEmpty()) {
            callback.calculateDeliveryCost(deliveryCity, selectedDeliveryOption)
        }
    }

    // Withdraw cashback
    var withdrawCashbackPoints by rememberSaveable(Unit, BooleanSaver) {
        mutableStateOf(false)
    }
    var withdrawAmount by rememberSaveable(Unit, StringSaver) {
        mutableStateOf("")
    }

    LaunchedEffect(withdrawAmount, withdrawCashbackPoints) {
        if (!withdrawCashbackPoints) {
            callback.calculatePointsDiscount(0)
        }
        if (withdrawAmount.isNotEmpty() && withdrawAmount.isDigitsOnly()) {
            val pointsAmount = withdrawAmount.toInt()
            callback.calculatePointsDiscount(pointsAmount)
        }
    }

    val enableCheckoutButton by remember {
        derivedStateOf {
            val personalInfoValid = validatePersonal(name, surname, phone, email)
            val deliveryValid = validateAddress(selectedDeliveryOption, uiState.addresses, newAddress)
            val withdrawPointsValid = !withdrawCashbackPoints || withdrawAmount.isNotEmpty()

            personalInfoValid && deliveryValid && withdrawPointsValid
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {

        PersonalData(
            name = name,
            surname = surname,
            phoneNumber = phone,
            email = email,
            onChangeName = { name = it },
            onChangeSurname = { surname = it },
            onChangePhone = { phone = it },
            onChangeEmail = { email = it },
        )

        Spacer(modifier = Modifier.height(30.dp))

        ReceiptMethodAndAddress(
            deliveryOption = selectedDeliveryOption,
            addresses = uiState.addresses,
            selected = selectedAddress,
            saveNewAddress = saveNewAddress,
            onSelectAddress = { selectedAddress = it },
            onAddressListChanged = callback::updateAddressesList,
            onSelectDeliveryOption = { selectedDeliveryOption = it },
            onChangeNewAddress = { newAddress = it },
            onCheckSaveAddress = { saveNewAddress = !saveNewAddress },
            openAddressMap = openAddressMap,
            onClick = { showCDEKMap = true }
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorScheme.secondary)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))

        if (uiState.isLoyalityAvailable) {
            SystemLoyality(
                isWithdraw = withdrawCashbackPoints,
                balance = uiState.cardBalance,
                availableBalance = uiState.availableBalance,
                cashback = uiState.cashback,
                amount = withdrawAmount,
                onChangeAmount = { withdrawAmount = it },
                onCheckWithdraw = { state ->
                    if (!state) {
                        withdrawAmount = ""
                    }
                    withdrawCashbackPoints = state
                },
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorScheme.secondary)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))

        OrderProductsList(
            orderItems = uiState.orderItems,
            onClickProduct = callback::onClickProduct
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))

        Checkout(
            productsCount = uiState.orderItems.size,
            total = uiState.total,
            discount = uiState.discount,
            deliveryCost = uiState.deliveryCost,
            pointsDiscount = uiState.pointsDiscount,
            cashbackPoints = uiState.cashbackPoints,
            summary = uiState.summary
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.spacers.extraLarge
                )
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.make_order_button),
                backgroundColor = colorScheme.primary,
                isEnabled = enableCheckoutButton,
                textColor = ColorTokens.Graphite,
                onClick = {
                    val address = if (selectedDeliveryOption == DeliveryOption.COURIER) {
                        if (uiState.addresses.isEmpty()) {
                            with(newAddress) {
                                "${city.trim()}, ${street.trim()}, $house, $flat"
                            }
                        } else {
                            uiState.addresses[selectedAddress].fullAddress
                        }
                    } else ""

                    if (saveNewAddress) {
                        callback.saveAddress(newAddress)
                    }

                    callback.onCheckout(
                        CheckoutResult(
                            name = name,
                            surname = surname,
                            email = email,
                            phone = phone,
                            saveInProfile = saveInProfileCheckboxState,
                            address = address,
                            deliveryOption = selectedDeliveryOption,
                            pickupPoint = selectedPickupPoint,
                            withdrawAmount = withdrawAmount.toIntOrNull() ?: 0,
                            withdrawPoints = withdrawCashbackPoints
                        )
                    )

                }
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.medium)
            )
            PrivacyPolicyText(
                buttonLabel = stringResource(id = R.string.make_order_button),
                onClick = callback::openPrivacyPolicy
            )
        }

    }

    if (selectedDeliveryOption == DeliveryOption.PICK_UP) {

        AnimatedVisibility(
            visible = showCDEKMap,
            enter = fadeIn() + scaleIn(tween(150)),
            exit = fadeOut() + scaleOut(tween(150))
        ) {
            MapViewScreen(
                modifier = Modifier
                    .background(color = ColorTokens.Graphite),
                pickupPoints = uiState.pickupPoints,
                onClose = { showCDEKMap = false },
                pickupCity = uiState.pickupCity,
                selectedPickupPoint = selectedPickupPoint,
                onChangePickupCity = callback::onChangePickupCity,
                onSelectPickupPoint = { selectedPickupPoint = it }
            )
        }
    }

}


@Composable
private fun OrderProductsList(
    orderItems: List<Product>,
    onClickProduct: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacers.extraLarge)
    ) {
        repeat(orderItems.size) { i ->
            val isNotLast = remember { orderItems.lastIndex != i }
            val isNotFirst = remember { i != 0 }

            val item = remember { orderItems[i] }

            if (isNotFirst) {
                Spacer(
                    modifier = Modifier
                        .height(MaterialTheme.spacers.medium)
                )
            }

            CProduct(
                model = item,
                onClick = { onClickProduct(item.id) }
            )

            if (isNotLast) {
                Spacer(
                    modifier = Modifier
                        .height(MaterialTheme.spacers.medium)
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = colorScheme.secondary.copy(0.1f),
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
private fun CProduct(
    modifier: Modifier = Modifier,
    model: Product,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        ProductPhoto(
            modifier = Modifier
                .width(100.dp)
                .fillMaxSize(),
            link = model.photos.firstOrNull().orEmpty()
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraMedium))
        Information(
            price = model.actualPrice,
            cashback = model.cashbackPoints,
            discount = model.oldPrice,
            keepOldPrice = false,
            discountPercent = model.discount,
            isDiscountPercent = model.isPercentDiscount,
            title = model.title,
            condition = "",
            size = "",
            showSizeAndCondition = false
        )
    }

}