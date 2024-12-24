package com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.text.saver.BooleanSaver
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes


@Composable
fun Buttons(
    isInCart: Boolean,
    isFavourite: Boolean,
    isInStock: Boolean = true,
    onClickBuy: () -> Unit,
    onClickHeart: () -> Unit
) {
    var inCartLocal by rememberSaveable(isInCart, BooleanSaver) { mutableStateOf(isInCart) }
    var isFavouriteLocal by rememberSaveable(
        isFavourite,
        BooleanSaver
    ) { mutableStateOf(isFavourite) }

    val favouriteButtonBorderColor =
        if (isFavouriteLocal) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondary.copy(0.1f)
        }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.paddings.extraLarge
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (!inCartLocal) {
            Button(
                modifier = Modifier.weight(0.8f),
                text = stringResource(id = R.string.buy),
                textColor = ColorTokens.Graphite,
                isEnabled = isInStock,
                backgroundColor = MaterialTheme.colorScheme.primary,
                onClick = { inCartLocal = !inCartLocal; onClickBuy() }
            )
        } else {
            ButtonBuy(
                text = stringResource(R.string.in_cart),
                onClick = { inCartLocal = !inCartLocal; onClickBuy() }
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        IconButton(
            modifier = Modifier.weight(0.2f),
            icon = ImageVector.vectorResource(id = R.drawable.ic_heart),
            tint = MaterialTheme.colorScheme.secondary,
            borderColor = favouriteButtonBorderColor,
            backgroundColor = Color.Transparent,
            onClick = { isFavouriteLocal = !isFavouriteLocal; onClickHeart() }
        )
    }
}

@Composable
private fun RowScope.ButtonBuy(
    text: String,
    onClick: () -> Unit
){
    var labelFontSize by remember { mutableStateOf(16.sp) }
    Box(
        modifier = Modifier
            .width(110.dp)
            .height(45.dp)
            .background(color = colorScheme.primary, shape = MegahandShapes.medium),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colorScheme.secondary,
            fontSize = 16.sp,
            onTextLayout = {
                if (it.hasVisualOverflow) {
                    labelFontSize *= 0.9f
                }
            },
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500)))
        )
    }
}

