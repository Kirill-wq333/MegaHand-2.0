package com.evothings.mhand.presentation.feature.card.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.card.viewmodel.enumeration.CardFilterType
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun HistoryBar(
    modifier: Modifier = Modifier,
    isFilterPickerExpanded: Boolean,
    currentFilter: CardFilterType,
    onExpand: () -> Unit
){
    val iconRes = remember {
        if (isFilterPickerExpanded) R.drawable.ic_chevron_top else R.drawable.ic_chevron_bottom
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.paddings.extraLarge)
            .clickable (
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onExpand
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.transactions_history),
            color = colorScheme.secondary,
            style = MegahandTypography.headlineMedium,
            modifier = Modifier
                .padding(vertical = MaterialTheme.paddings.medium)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = currentFilter.title,
                color = colorScheme.secondary,
                style = MegahandTypography.labelLarge,
                modifier = Modifier
                    .padding(vertical = MaterialTheme.paddings.medium)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.tiny))
            Icon(
                imageVector = ImageVector.vectorResource(iconRes),
                contentDescription = null,
                tint = colorScheme.secondary,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.paddings.medium)
            )
        }
    }
}