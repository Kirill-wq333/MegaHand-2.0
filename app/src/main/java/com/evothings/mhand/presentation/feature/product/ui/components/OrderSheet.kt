package com.evothings.mhand.presentation.feature.product.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.product.components.price.OldPrice
import com.evothings.mhand.presentation.feature.shared.product.components.price.PriceSize
import com.evothings.mhand.presentation.feature.shared.text.saver.BooleanSaver
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun OrderSheet(
    cashback: Double,
    price: Double,
    discountPercent: Boolean,
    discount: Double,
    oldPrice: Double,
    isFavourite: Boolean,
    inStock: Boolean = true,
    isInCart: Boolean,
    onBuy: () -> Unit,
    onFavorite: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.paddings.giant,
                    horizontal = MaterialTheme.paddings.extraGiant
                )
        ) {
            OrderSheetItem(
                discount = discount,
                discountPercent = discountPercent,
                price = price,
                cashback = cashback,
                oldPrice = oldPrice
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
            OrderSheetButton(
                isFavourite = isFavourite,
                inStock = inStock,
                isInCart = isInCart,
                onFavorite = onFavorite,
                onBuy = onBuy
            )
        }
    }
}



@Composable
fun OrderSheetItem(
    discount: Double,
    discountPercent: Boolean,
    price: Double,
    oldPrice: Double,
    cashback: Double
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Price(
            price = "$price",
            cashback = "$cashback",
        )
        Spacer(
            modifier = Modifier
                .width(MaterialTheme.spacers.normal)
        )
        OldPrice(
            enabled = (discount > 0),
            value = oldPrice,
            discount = discount,
            isDiscountPercent = discountPercent,
            size = PriceSize.Big
        )
    }

}


@Composable
private fun Price(
    price: String,
    cashback: String
) {
    Row(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.paddings.extraLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$price ₽",
            color = colorScheme.secondary,
            style = MegahandTypography.headlineSmall,
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        Cashback(cashback = cashback)
    }
}

@Composable
private fun Cashback(
    cashback: String
) {

    Box(
        modifier = Modifier
            .background(
                color = colorScheme.inverseSurface.copy(0.1f),
                shape = shapes.large
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.paddings.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_prize),
                contentDescription = null,
                tint = colorScheme.inverseSurface
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.tiny))
            Text(
                text = cashback,
                color = colorScheme.secondary,
                style = MegahandTypography.bodyMedium
            )
        }
    }

}


@Composable
private fun OrderSheetButton(
    isInCart: Boolean,
    isFavourite: Boolean,
    inStock: Boolean = true,
    onBuy: () -> Unit,
    onFavorite: () -> Unit
){

    var inCartLocal by rememberSaveable(isInCart, BooleanSaver) { mutableStateOf(isInCart) }
    var isFavouriteLocal by rememberSaveable(isFavourite, BooleanSaver) { mutableStateOf(isFavourite) }

    val favouriteButtonBorderColor =
        if (isFavouriteLocal) {
            colorScheme.primary
        } else {
            colorScheme.secondary.copy(0.1f)
        }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (!inCartLocal) {
            Button(
                modifier = Modifier
                    .weight(.8f),
                text = stringResource(R.string.buy),
                textColor = colorScheme.secondary,
                isEnabled = inStock,
                backgroundColor = colorScheme.primary,
                onClick = { inCartLocal = !inCartLocal; onBuy() }
            )
        }else{
            InCartButton(
                onClick = { inCartLocal = !inCartLocal; onBuy() }
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        IconButton(
            modifier = Modifier
                .weight(.2f),
            icon = ImageVector.vectorResource(R.drawable.ic_favourite),
            tint = colorScheme.secondary,
            borderColor = favouriteButtonBorderColor,
            onClick = { isFavouriteLocal = !isFavouriteLocal; onFavorite() }
        )
    }
}

@Composable
private fun RowScope.InCartButton(
    onClick: () -> Unit
) {
    var labelFontSize by remember { mutableStateOf(16.sp) }

    Button(
        modifier = Modifier.weight(0.7f),
        backgroundColor = colorScheme.secondary,
        onClick = onClick,
        content = {
            Text(
                text = stringResource(id = R.string.in_cart),
                color = colorScheme.onSecondary,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                fontSize = labelFontSize,
                onTextLayout = {
                    if (it.hasVisualOverflow) {
                        labelFontSize *= 0.9f
                    }
                },
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                        horizontal = 16.dp
                    )
            )
        }
    )
}

