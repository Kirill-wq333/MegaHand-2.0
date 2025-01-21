package com.evothings.mhand.presentation.feature.product.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.product.ui.components.BrandProduct
import com.evothings.mhand.presentation.feature.product.ui.components.OrderSheet
import com.evothings.mhand.presentation.feature.product.ui.components.ParametersProduct
import com.evothings.mhand.presentation.feature.product.ui.components.SliderPhoto
import com.evothings.mhand.presentation.feature.product.viewmodel.ProductContract
import com.evothings.mhand.presentation.feature.product.viewmodel.ProductViewModel
import com.evothings.mhand.presentation.feature.shared.header.ui.Header
import com.evothings.mhand.presentation.feature.shared.header.ui.HeaderProvider
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.pullToRefresh.PullRefreshLayout
import com.evothings.mhand.presentation.feature.shared.screen.ServerErrorScreen
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

interface ProductInfoCallback {
    fun onBack()
    fun addToCart()
    fun toggleFavourite()
    fun reload()
}

@Composable
fun ProductInfoScreen(
    modifier: Modifier = Modifier,
    id: Int,
    vm: ProductViewModel,
    onBack: () -> Unit
) {

    val state by vm.state.collectAsStateWithLifecycle()
    val product by vm.product.collectAsState()

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
        product = product,
        callback = callback
    )
}


@Composable
private fun ProductInfoContent(
    modifier: Modifier = Modifier,
    state: ProductContract.State,
    product: Product,
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
                            product = product,
                            callback = callback
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
            isInCart = product.isInCart
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
}


@Composable
private fun Content(
    model: Product,
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
            inStock = inStock
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        Text(
            text = model.description,
            textAlign = TextAlign.Start,
            style = MegahandTypography.bodyLarge,
            color = colorScheme.secondary.copy(0.6f),
            modifier = Modifier
                .padding(horizontal = MaterialTheme.paddings.extraGiant)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        BrandProduct(
            modifier = Modifier
                .padding(start = MaterialTheme.paddings.extraGiant),
            brand = "https://s3-alpha-sig.figma.com/img/997c/f6cf/1ca7984783573f3aa9869d9638c2aeef?Expires=1737331200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=UO~tqmhTxGO5NmiQbKeNZVHi1tl~YR4X1EevQZDX4jLkrRtBgggr91p5eAnLDVsjer-J3Qt~ASPcQNgel~rGgTc8WfCCWr9DuAAwjJmbDYJdMzpwG-NPkPTHgmzWX9~aYOW47QtvasQre5328BGe74pL2fEJPRNNRsl6-taMakIuWcokkpyBibI9aBydQwXfhCfk9eLMJLvIKFZDJjeaMuXXWjJ6vbqBRxjAWJDci8TUEfGWpaoH-d8rSmkQ~EkVWVjqV8A2vfR~YMmQsGDGMPtouSjIoaLRmE3tOd4wkYi1jnPL4YXm6g4tzKD1gMs1tG2wzSmmiIbaA9Ju~GG8LA__"
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

@Preview
@Composable
fun PreviewProductInfoScreen(){
    MegahandTheme(false){

    }
}