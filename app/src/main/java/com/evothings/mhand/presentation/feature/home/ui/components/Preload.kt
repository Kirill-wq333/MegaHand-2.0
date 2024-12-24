package com.evothings.mhand.presentation.feature.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Buttons
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Discount
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Price
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.SizeAndStars
import com.evothings.mhand.presentation.feature.shared.product.callback.ProductCardCallback
import com.evothings.mhand.presentation.feature.shared.product.components.ProductTitle
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun PreloadItem(
    model: Product,
    callback: ProductCardCallback
){

    when(model.availability) {
        Product.Availability.IN_STOCK -> {
            InStockPreloadItem(
                model = model,
                onClick = { callback.openProductDetailScreen(model.id) },
                onClickBuy = { callback.addToCart(model.id) },
                onClickHeart = { callback.toggleFavourite(model.id) },
                discount = model.discount
            )
        }

        Product.Availability.OUT_OF_STOCK -> {
            OutOfStockPreloadItem(
                model = model,
                onClick = { callback.openProductDetailScreen(model.id) }
            )
        }
    }

}

@Composable
fun InStockPreloadItem(
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
        Column() {
            PhotoSlider(product = model.photos)
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraMedium))
            Price(price = model.actualPrice, cashback = model.cashbackPoints)
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.tiny))
            if (keepOldPrice || discount > 0) {
                Discount(
                    discount = model.oldPrice,
                    discountPercent = model.discount,
                    isDiscountPercent = model.isPercentDiscount,
                    enabled = (discount > 0)
                    )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
            Text(
                text = model.title + "\n",
                color = colorScheme.secondary.copy(0.6f),
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.paddings.extraLarge
                    )
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
            if (showSizeAndCondition) {
                SizeAndStars(estimation = model.condition, textSize = model.size)
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
fun OutOfStockPreloadItem(
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
        PhotoSlider(product = model.photos)
        Text(
            text = stringResource(R.string.out_of_stock),
            style = MaterialTheme.typography.headlineMedium,
            color = colorScheme.secondary
        )
        Text(
            text = model.title + "\n",
            color = colorScheme.secondary.copy(0.6f),
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.paddings.extraLarge
                )
        )
    }
}

@Composable
fun PhotoSlider(
    product: List<String>
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = shapes.large)
            .height(180.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = product,
            contentDescription = null,
            modifier = Modifier
                .clip(shape = shapes.large)
        )
    }
}