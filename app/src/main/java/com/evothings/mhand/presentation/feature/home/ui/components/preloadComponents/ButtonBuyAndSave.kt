package com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.text.saver.BooleanSaver
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers


@Composable
fun Buttons(
    modifier: Modifier = Modifier,
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
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (!inCartLocal) {
            Button(
                modifier = Modifier.weight(0.7f),
                text = stringResource(id = R.string.buy),
                textColor = ColorTokens.Graphite,
                isEnabled = isInStock,
                backgroundColor = MaterialTheme.colorScheme.primary,
                onClick = { inCartLocal = !inCartLocal; onClickBuy() }
            )
        } else {
            InCartButton(
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
private fun RowScope.InCartButton(
    onClick: () -> Unit
) {
    var labelFontSize by remember { mutableStateOf(16.sp) }

    Button(
        modifier = Modifier.weight(0.7f),
        backgroundColor = MaterialTheme.colorScheme.secondary,
        onClick = onClick,
        content = {
            Text(
                text = stringResource(id = R.string.in_cart),
                color = MaterialTheme.colorScheme.onSecondary,
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
