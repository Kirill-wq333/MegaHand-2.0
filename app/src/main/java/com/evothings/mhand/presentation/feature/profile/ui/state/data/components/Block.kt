package com.evothings.mhand.presentation.feature.profile.ui.state.data.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
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
    discount: Int = 3,
    openBottomSheet: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.secondary.copy(.05f),
                shape = MegahandShapes.large
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.giant),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {
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
            Row {
                if (visibleDiscount) {
                    Text(
                        text = "$discount%",
                        color = colorScheme.secondary.copy(.6f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                IconButton(
                    icon = if (openBottomSheet) ImageVector.vectorResource(R.drawable.ic_chevron_bottom) else ImageVector.vectorResource(
                        R.drawable.ic_chevron_top
                    ),
                    tint = colorScheme.secondary.copy(.4f),
                    onClick = {}
                )
            }
        }
    }
}