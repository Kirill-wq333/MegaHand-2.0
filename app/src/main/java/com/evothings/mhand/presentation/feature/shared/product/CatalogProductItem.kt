package com.evothings.mhand.presentation.feature.shared.product

//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.RowScope
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.res.vectorResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.evothings.domain.feature.product.model.Product
//import com.evothings.domain.util.Mock
//import com.evothings.mhand.R
//import com.evothings.mhand.presentation.feature.shared.button.Button
//import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
//import com.evothings.mhand.presentation.feature.shared.product.callback.EmptyProductCardCallback
//import com.evothings.mhand.presentation.feature.shared.product.callback.ProductCardCallback
//import com.evothings.mhand.presentation.feature.shared.product.components.PhotosSlider
//import com.evothings.mhand.presentation.feature.shared.product.components.CardProductInformation
//import com.evothings.mhand.presentation.feature.shared.product.components.ProductPhoto
//import com.evothings.mhand.presentation.feature.shared.product.components.ProductTitle
//import com.evothings.mhand.presentation.feature.shared.text.saver.BooleanSaver
//import com.evothings.mhand.presentation.theme.MegahandTheme
//import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
//import com.evothings.mhand.presentation.theme.paddings
//import com.evothings.mhand.presentation.theme.spacers
//
//@Composable
//fun CatalogProductItem(
//    model: Product,
//    callback: ProductCardCallback
//) {
//
//    when(model.availability) {
//        Product.Availability.IN_STOCK -> {
//            InStockCard(
//                model = model,
//                onClick = { callback.openProductDetailScreen(model.id) },
//                onClickBuy = { callback.addToCart(model.id) },
//                onClickHeart = { callback.toggleFavourite(model.id) }
//            )
//        }
//
//        Product.Availability.OUT_OF_STOCK -> {
//            OutOfStockCard(
//                photo = model.photos.firstOrNull().orEmpty(),
//                title = model.title,
//                onClick = { callback.openProductDetailScreen(model.id) }
//            )
//        }
//    }
//}
//
//@Composable
//private fun InStockCard(
//    model: Product,
//    onClick: () -> Unit,
//    onClickBuy: () -> Unit,
//    onClickHeart: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .width(180.dp)
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
//                onClick = onClick
//            ),
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        PhotosSlider(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(180.dp),
//            images = model.photos
//        )
//        Spacer(
//            modifier = Modifier.height(15.dp)
//        )
//        CardProductInformation(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = MaterialTheme.paddings.large),
//            actualPrice = model.actualPrice,
//            oldPrice = model.oldPrice,
//            cashbackPoints = model.cashbackPoints,
//            discount = model.discount,
//            isPercentDiscount = model.isPercentDiscount,
//            title = model.title + "\n",
//            titleMaxLines = 2,
//            size = model.size,
//            condition = model.condition
//        )
//        Spacer(
//            modifier = Modifier.height(15.dp)
//        )
//        ProductActions(
//            isInCart = model.isInCart,
//            isFavourite = model.isFavourite,
//            onClickBuy = onClickBuy,
//            onClickHeart = onClickHeart,
//            modifier = Modifier.padding(
//                horizontal = MaterialTheme.paddings.large
//            )
//        )
//    }
//}
//
//@Composable
//private fun OutOfStockCard(
//    photo: String,
//    title: String,
//    onClick: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .alpha(0.3f)
//            .width(180.dp)
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
//                onClick = onClick
//            ),
//        verticalArrangement = Arrangement.spacedBy(15.dp)
//    ) {
//        ProductPhoto(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(180.dp),
//            link = photo
//        )
//        Text(
//            text = stringResource(R.string.out_of_stock),
//            style = MaterialTheme.typography.headlineMedium,
//            color = MaterialTheme.colorScheme.secondary
//        )
//        ProductTitle(
//            value = title,
//            maxLines = 3
//        )
//    }
//}
//
//@Composable
//fun ProductActions(
//    modifier: Modifier,
//    isInCart: Boolean,
//    isFavourite: Boolean,
//    isInStock: Boolean = true,
//    onClickBuy: () -> Unit,
//    onClickHeart: () -> Unit
//) {
//
//    var inCartLocal by rememberSaveable(isInCart, BooleanSaver) { mutableStateOf(isInCart) }
//    var isFavouriteLocal by rememberSaveable(isFavourite, BooleanSaver) { mutableStateOf(isFavourite) }
//
//    val favouriteButtonBorderColor =
//        if (isFavouriteLocal) {
//            MaterialTheme.colorScheme.primary
//        } else {
//            MaterialTheme.colorScheme.secondary.copy(0.1f)
//        }
//
//    Row(
//        modifier = modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        if (!inCartLocal) {
//            Button(
//                modifier = Modifier.weight(0.8f),
//                text = stringResource(id = R.string.buy),
//                textColor = ColorTokens.Graphite,
//                isEnabled = isInStock,
//                backgroundColor = MaterialTheme.colorScheme.primary,
//                onClick = { inCartLocal = !inCartLocal; onClickBuy() }
//            )
//        } else {
//           InCartButton(
//               onClick = { inCartLocal = !inCartLocal; onClickBuy() }
//           )
//        }
//        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
//        IconButton(
//            modifier = Modifier.weight(0.2f),
//            icon = ImageVector.vectorResource(id = R.drawable.ic_heart),
//            tint = MaterialTheme.colorScheme.secondary,
//            borderColor = favouriteButtonBorderColor,
//            backgroundColor = Color.Transparent,
//            onClick = { isFavouriteLocal = !isFavouriteLocal; onClickHeart() }
//        )
//    }
//}
//
//@Composable
//private fun RowScope.InCartButton(
//    onClick: () -> Unit
//) {
//    var labelFontSize by remember { mutableStateOf(16.sp) }
//
//    Button(
//        modifier = Modifier.weight(0.7f),
//        backgroundColor = MaterialTheme.colorScheme.secondary,
//        onClick = onClick,
//        content = {
//            Text(
//                text = stringResource(id = R.string.in_cart),
//                color = MaterialTheme.colorScheme.onSecondary,
//                style = MaterialTheme.typography.labelLarge,
//                maxLines = 1,
//                fontSize = labelFontSize,
//                onTextLayout = {
//                    if (it.hasVisualOverflow) {
//                        labelFontSize *= 0.9f
//                    }
//                },
//                modifier = Modifier
//                    .padding(
//                        vertical = 12.dp,
//                        horizontal = 16.dp
//                    )
//            )
//        }
//    )
//}
//
//@Preview
//@Composable
//private fun ProductCardPreview() {
//    MegahandTheme {
//        Surface(color = MaterialTheme.colorScheme.surface) {
//            CatalogProductItem(
//                model = Mock.demoProduct,
//                callback = EmptyProductCardCallback
//            )
//        }
//    }
//}