package com.evothings.mhand.presentation.feature.shared.product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Buttons
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Information
import com.evothings.mhand.presentation.feature.shared.product.callback.ProductCardCallback
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import kotlinx.coroutines.delay

@Composable
fun ProductItem(
    model: Product,
    callback: ProductCardCallback
){

    when(model.availability) {
        Product.Availability.IN_STOCK -> {
            InStockProductItem(
                model = model,
                onClick = { callback.openProductDetailScreen(model.id) },
                onClickBuy = { callback.addToCart(model.id) },
                onClickHeart = { callback.toggleFavourite(model.id) },
                discount = model.discount
            )
        }

        Product.Availability.OUT_OF_STOCK -> {
            OutOfStockProductItem(
                model = model,
                onClick = { callback.openProductDetailScreen(model.id) }
            )
        }
    }

}

@Composable
fun InStockProductItem(
    model: Product,
    onClick: () -> Unit,
    keepOldPrice: Boolean = true,
    discount: Double,
    onClickHeart: () -> Unit,
    onClickBuy: () -> Unit,
    showSizeAndCondition: Boolean = true,
){
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .width(180.dp),
        contentAlignment = Alignment.TopStart
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
                .height(365.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                PhotoSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    product = model.photos
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraMedium))
                Information(
                    discount = discount,
                    discountPercent = model.discount,
                    isDiscountPercent = model.isPercentDiscount,
                    showSizeAndCondition = showSizeAndCondition,
                    condition = model.condition,
                    keepOldPrice = keepOldPrice,
                    price = model.actualPrice,
                    cashback = model.cashbackPoints,
                    size = model.size,
                    title = model.title + "\n"
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraMedium))
            Buttons(
                isInCart = model.isInCart,
                isFavourite = model.isFavourite,
                onClickBuy = onClickBuy,
                onClickHeart = onClickHeart,
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.paddings.large
                )
            )
        }
    }
}


@Composable
fun OutOfStockProductItem(
    model: Product,
    onClick: () -> Unit
){
    Column(
        modifier = Modifier
            .alpha(0.3f)
            .width(180.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        PhotoSlider(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            product = model.photos
        )
        Text(
            text = stringResource(R.string.out_of_stock),
            style = MaterialTheme.typography.headlineMedium,
            color = colorScheme.secondary
        )
        Text(
            text = model.title + "\n",
            maxLines = 3,
            color = colorScheme.secondary.copy(0.6f),
            style = MegahandTypography.bodyLarge,
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.paddings.extraLarge
                )
        )
    }
}

@Composable
fun PhotoSlider(
    modifier: Modifier = Modifier,
    product: List<String>
) {

    val pagerState = rememberPagerState { product.size }

    val indicatorVisibilityTimer = 1000L
    val isIndicatorVisible = remember { mutableStateOf(false) }

    LaunchedEffect(isIndicatorVisible.value, pagerState.currentPage) {
        if (isIndicatorVisible.value) {
            delay(indicatorVisibilityTimer)
            isIndicatorVisible.value = false
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (product.size in 0..1) {
           ProductPhoto(
                link = product.firstOrNull().orEmpty()
            )
        } else {
            Pager(
                state = pagerState,
                photos = product,
                onPress = { isIndicatorVisible.value = true }
            )
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = isIndicatorVisible.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
               PageIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    pages = product.size,
                    current = pagerState.currentPage
                )
            }
        }
    }

}

@Composable
private fun BoxScope.Pager(
    state: PagerState,
    photos: List<String>,
    onPress: () -> Unit
) {
    HorizontalPager(
        modifier = Modifier
            .matchParentSize()
            .pointerInput(PointerEventType.Press) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.type == PointerEventType.Press) onPress()
                    }
                }
            },
        pageSpacing = MaterialTheme.spacers.tiny,
        state = state
    ) { page ->
        ProductPhoto(
            link = photos[page]
        )
    }
}

@Composable
private fun ProductPhoto(
    modifier: Modifier = Modifier,
    link: String
) {
    AsyncImage(
        model = link,
        error =painterResource(id = R.drawable.no_photo_placeholder) ,
        placeholder = painterResource(R.drawable.image_placeholder),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = shapes.large)
    )
}

@Composable
private fun PageIndicator(
    modifier: Modifier,
    pages: Int,
    current: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.tiny)
    ) {
        repeat(pages) {
            Box(
                modifier = Modifier
                    .weight(0.2f)
                    .height(1.dp)
                    .background(
                        color = colorScheme.secondary.copy(
                            alpha = if (current == it) 1.0f else 0.2f
                        )
                    )
            )
        }
    }
}