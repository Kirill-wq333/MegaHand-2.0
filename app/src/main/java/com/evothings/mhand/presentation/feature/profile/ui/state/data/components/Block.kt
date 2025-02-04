package com.evothings.mhand.presentation.feature.profile.ui.state.data.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.text.saver.BooleanSaver
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun Block(
    modifier: Modifier = Modifier,
    visiblePrize: Boolean = false,
    text: String,
    visibleText: Boolean = false,
    visibleDiscount: Boolean = false,
    onExpandStateChange: (Boolean) -> Unit = {},
    discount: Int = 3,
    content: @Composable (ColumnScope) -> Unit
) {

    var isExpanded by rememberSaveable(Unit, BooleanSaver) { mutableStateOf(false) }

    val icon = remember(isExpanded) {
        if (isExpanded) R.drawable.ic_chevron_top else R.drawable.ic_chevron_bottom
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.secondary.copy(.05f),
                shape = MegahandShapes.large
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.giant),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { isExpanded = !isExpanded; onExpandStateChange(isExpanded) }
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (visiblePrize) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = colorScheme.inverseSurface.copy(.1f),
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_prize),
                                contentDescription = null,
                                tint = colorScheme.inverseSurface,
                                modifier = Modifier
                                    .padding(MaterialTheme.paddings.medium)
                            )
                        }
                        Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
                    }
                    Column {
                        if (visibleText) {
                            Text(
                                text = stringResource(R.string.referal_code_card_heading),
                                color = colorScheme.secondary.copy(.6f),
                                style = MaterialTheme.typography.bodyMedium

                            )
                        }
                        Text(
                            text = text,
                            color = colorScheme.secondary,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (visibleDiscount) {
                        Text(
                            text = "$discount%",
                            color = colorScheme.secondary,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    IconButton(
                        icon = ImageVector.vectorResource(icon),
                        tint = colorScheme.secondary.copy(.4f),
                        iconPadding = 0.dp,
                        onClick = {}
                    )
                }
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column {
                    Spacer(
                        modifier = Modifier
                            .height(MaterialTheme.spacers.extraLarge)
                    )
                    content.invoke(this)
                }
            }
        }
    }

}

@Composable
fun BlockCashback(
    cashback: Int
) {
    Block(
        text = stringResource(R.string.cashback),
        visibleDiscount = true,
        discount = cashback,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val levelDescription = remember(cashback) {
                    when (cashback) {
                        0 -> R.string.cashback_level_0
                        1 -> R.string.cashback_level_1
                        2 -> R.string.cashback_level_2
                        3 -> R.string.cashback_level_3
                        4 -> R.string.cashback_level_4
                        5 -> R.string.cashback_level_5
                        else -> 0
                    }
                }

                Text(
                    text = stringResource(levelDescription),
                    color = colorScheme.secondary.copy(.6f),
                    style = MegahandTypography.bodyMedium
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))

                DiscountBlock(
                    cashback = cashback
                )
            }
        }
    )
}

@Composable
private fun DiscountBlock(cashback: Int) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(5) { index ->

            val enabled = remember {
                index in 0 until cashback
            }

            DiscountBlockItem(
                text = (index + 1),
                isEnabled = enabled
            )
            if (index != 4) {
                Spacer(
                    modifier = Modifier
                        .width(MaterialTheme.spacers.small)
                )
            }
        }
    }

}

@Composable
private fun DiscountBlockItem(
    modifier: Modifier = Modifier,
    text: Int,
    isEnabled: Boolean = true
) {

    Column(
        modifier = modifier
            .width(57.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Box (
            Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(
                    color = colorScheme.inverseSurface,
                    shape = MegahandShapes.extraLarge
                )
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.tiny))
        Text(
            text = "$text%",
            color = colorScheme.secondary.copy( alpha = if (isEnabled) 1.0f else 0.1f),
            style = MegahandTypography.bodyLarge,
        )
    }

}

@Preview
@Composable
private fun BlockPreview() {

    MegahandTheme {
        Block(
            text = stringResource(R.string.cashback),
            visibleDiscount = true,
            discount = 5,
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    BlockCashback(
                        cashback = 5,
                    )
                }
            }
        )
    }

}