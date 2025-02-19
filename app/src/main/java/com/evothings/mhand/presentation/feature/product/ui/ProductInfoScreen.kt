package com.evothings.mhand.presentation.feature.product.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.home.model.Brand
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.product.ui.components.BrandProduct
import com.evothings.mhand.presentation.feature.product.ui.components.OrderSheet
import com.evothings.mhand.presentation.feature.product.ui.components.ParametersProduct
import com.evothings.mhand.presentation.feature.product.ui.components.SliderPhoto
import com.evothings.mhand.presentation.feature.product.viewmodel.ProductContract
import com.evothings.mhand.presentation.feature.product.viewmodel.ProductViewModel
import com.evothings.mhand.presentation.feature.shared.header.ui.HeaderProvider
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.pullToRefresh.PullRefreshLayout
import com.evothings.mhand.presentation.feature.shared.screen.ServerErrorScreen
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

data class ProductInfoUiState(
    val product: Product,
    val brand: Brand
)

interface ProductInfoCallback {
    fun onBack()
    fun addToCart()
    fun toggleFavourite()
    fun reload()
}

@Composable
fun ProductInfoScreen(
    id: Int,
    vm: ProductViewModel,
    onBack: () -> Unit
) {

    val state by vm.state.collectAsStateWithLifecycle()
    val product by vm.product.collectAsState()
    val brand by vm.brand.collectAsState()

    LaunchedEffect(Unit, id) {
        vm.handleEvent(ProductContract.Event.LoadProduct(id))
    }

    val callback = object : ProductInfoCallback {
        override fun onBack() = onBack()

        override fun reload() =
            vm.handleEvent(ProductContract.Event.Reload(id))

        override fun addToCart() =
            vm.handleEvent(ProductContract.Event.AddToCart(id))

        override fun toggleFavourite() =
            vm.handleEvent(ProductContract.Event.ToggleFavourite(id))
    }
    ProductInfoContent(
        state = state,
        uiState = ProductInfoUiState(
            product = product,
            brand = brand,
        ),
        callback = callback
    )
}


@Composable
private fun ProductInfoContent(
    state: ProductContract.State,
    uiState: ProductInfoUiState,
    callback: ProductInfoCallback
) {
    HeaderProvider(
        screenTitle = stringResource(R.string.product),
        turnButtonVisible = true,
        enableMapIconButton = false,
        onBack = callback::onBack
    ) { headerPadding ->
        Box(
            modifier = Modifier
                .padding(headerPadding)
        ) {
            when (state) {
                is ProductContract.State.Loading -> LoadingScreen()
                is ProductContract.State.Loaded ->
                    PullRefreshLayout(onRefresh = callback::reload) {
                        ProductContent(
                            product = uiState.product,
                            callback = callback,
                            brand = uiState.brand
                        )
                    }

                is ProductContract.State.ServerError ->
                    ServerErrorScreen(
                        onRefresh = callback::reload
                    )
            }
        }
    }
}

@Composable
private fun ProductContent(
    product: Product,
    brand: Brand,
    callback: ProductInfoCallback
) {
        Content(
            model = product,
            price = product.actualPrice,
            cashback = product.cashbackPoints,
            discountPercent = product.isPercentDiscount,
            discount = product.discount,
            oldPrice = product.oldPrice,
            inStock = (product.availability == Product.Availability.IN_STOCK),
            onBuy = callback::addToCart,
            onFavorite = callback::toggleFavourite,
            isFavourite = product.isFavourite,
            isInCart = product.isInCart,
            brand = brand
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
}


@Composable
private fun Content(
    model: Product,
    brand: Brand,
    oldPrice: Double,
    discount: Double,
    discountPercent: Boolean,
    cashback: Double,
    price: Double,
    onFavorite: () -> Unit,
    onBuy: () -> Unit,
    inStock: Boolean,
    isFavourite: Boolean,
    isInCart: Boolean
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {

        SliderPhoto(model = model.photos)
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Text(
            text = model.title,
            textAlign = TextAlign.Start,
            style = MegahandTypography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        ParametersProduct(
            size = model.size,
            color = model.color,
            quality = model.condition,
            properties = model.properties,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        Text(
            text = model.description,
            textAlign = TextAlign.Start,
            style = MegahandTypography.bodyLarge,
            color = colorScheme.secondary.copy(0.6f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.paddings.extraGiant)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        BrandProduct(
            modifier = Modifier
                .padding(start = MaterialTheme.paddings.extraGiant),
            brand = brand.toString()
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        OrderSheet(
            cashback = cashback,
            price = price,
            discount = discount,
            discountPercent = discountPercent,
            oldPrice = oldPrice,
            isFavourite = isFavourite,
            isInCart = isInCart,
            onBuy = onBuy,
            inStock = inStock,
            onFavorite = onFavorite
        )
    }
}
