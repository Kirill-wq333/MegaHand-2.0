package com.evothings.mhand.presentation.feature.cart.ui.components.productComponents

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.util.Mock
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Information
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.SizeAndStars
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.checkbox.CheckboxChecker
import com.evothings.mhand.presentation.feature.shared.product.components.PhotosSlider
import com.evothings.mhand.presentation.feature.shared.product.components.ProductPhoto
import com.evothings.mhand.presentation.feature.shared.text.saver.BooleanSaver
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun Cart(
    modifier: Modifier = Modifier,
    model: Product,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onClick: () -> Unit,
    toggleFavourite: () -> Unit,
    removeFromCart: () -> Unit
) {
    when(model.availability){
        Product.Availability.IN_STOCK -> {
            InStockCart(
                modifier = modifier,
                model = model,
                onFavorite = toggleFavourite,
                onTrash = removeFromCart,
                onClick = onClick,
                isCheckedLocal = isSelected,
                onCheck = onSelect
            )
        }
        Product.Availability.OUT_OF_STOCK -> {
            OutOfStockCart(
                modifier = modifier,
                model = model,
                onClick = onClick,
                onTrash = toggleFavourite,
                onFavorite = removeFromCart,
                isChecked = isSelected,
                onCheck = onSelect
            )
        }
    }
}


@Composable
fun InStockCart(
    modifier: Modifier = Modifier,
    model: Product,
    showSizeAndCondition: Boolean = true,
    keepOldPrice: Boolean = true,
    isCheckedLocal: Boolean ,
    onCheck: () -> Unit,
    onTrash: () -> Unit,
    onFavorite: () -> Unit,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        ImageSliderWithCheckbox(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize(),
            images = model.photos,
            isChecked = isCheckedLocal,
            onCheck = onCheck,
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraMedium))
        Column {
            Information(
                discount = model.oldPrice,
                discountPercent = model.discount,
                isDiscountPercent = model.isPercentDiscount,
                showSizeAndCondition = showSizeAndCondition,
                condition = model.condition,
                keepOldPrice = keepOldPrice,
                price = model.actualPrice,
                cashback = model.cashbackPoints,
                size = model.size,
                title = model.title
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraMedium))
            Action(
                onTrash = onTrash,
                onFavorite = onFavorite,
                isFavorite = model.isFavourite
            )
        }
    }

}

@Composable
fun OutOfStockCart(
    modifier: Modifier = Modifier,
    model: Product,
    onCheck: () -> Unit,
    showSizeAndCondition: Boolean = true,
    isChecked: Boolean,
    onClick: () -> Unit,
    onFavorite: () -> Unit,
    onTrash: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        ProductPhoto(
            modifier = Modifier
                .width(100.dp)
                .fillMaxSize(),
            link = model.photos.firstOrNull().orEmpty()
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        Text(
            text = model.title,
            color = colorScheme.secondary.copy(0.6f),
            style = MegahandTypography.bodyLarge,
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.paddings.extraLarge
                )
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        if (showSizeAndCondition) {
            SizeAndStars(estimation = model.condition, textSize = model.size)
        }
        Action(
            onFavorite = onFavorite,
            onTrash = onTrash,
            isFavorite = model.isFavourite
        )
    }
}

@Composable
private fun ImageSliderWithCheckbox(
    modifier: Modifier,
    images: List<String>,
    isChecked: Boolean,
    onCheck: () -> Unit
) {

    var isCheckedLocal by remember(isChecked) { mutableStateOf(isChecked) }

    Box(
        modifier = modifier
    ) {
        PhotosSlider(
            modifier = Modifier.fillMaxSize(),
            images = images
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(4.dp, 4.dp)
                .size(24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { isCheckedLocal = !isCheckedLocal; onCheck() }
                ),
            contentAlignment = Alignment.Center
        ) {
            CheckboxChecker(
                isChecked = isCheckedLocal
            )
        }
    }
}


@Composable
private fun Action(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onTrash: () -> Unit,
    onFavorite: () -> Unit
) {
    var isFavouriteLocal by rememberSaveable(isFavorite, BooleanSaver) { mutableStateOf(isFavorite) }
    val heartBorderColor =
        if (isFavouriteLocal) MaterialTheme.colorScheme.primary else Color.Transparent

    Row {
        IconButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_heart),
            tint = MaterialTheme.colorScheme.secondary,
            borderColor = heartBorderColor,
            backgroundColor = Color.Transparent,
            onClick = { isFavouriteLocal = !isFavouriteLocal; onFavorite() }
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.small))
        IconButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_trash),
            tint = MaterialTheme.colorScheme.secondary,
            borderColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            onClick = onTrash
        )
    }
}


@Preview
@Composable
private fun Cart() {
    MegahandTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorScheme.onSecondary)
        ) {
            InStockCart(
                modifier = Modifier
                    .padding(MaterialTheme.paddings.medium),
                model = Mock.demoProduct,
                isCheckedLocal = false,
                onClick = {},
                onCheck = {},
                onTrash = {},
                onFavorite = {},
            )
        }
    }
}