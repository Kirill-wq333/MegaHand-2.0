package com.evothings.mhand.presentation.feature.card.ui.components.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.card.viewmodel.enumeration.CardFilterType
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.radio.RadioChecker
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun TransactionFilterPicker(
    modifier: Modifier = Modifier,
    currentEntryIndex: Int,
    onDismiss: () -> Unit,
    onSelect: (Int) -> Unit
) {

    val entries = remember { CardFilterType.entries }
    var entryIndex by remember(currentEntryIndex) { mutableIntStateOf(currentEntryIndex) }

    Column(
        modifier = modifier
    ) {
        repeat(entries.size) { i ->
            val item = remember { entries[i] }

            PickerItem(
                text = item.title,
                isSelected = entryIndex == i,
                onSelect = { entryIndex = i }
            )
        }
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.medium)
        )
        Actions(
            onCancel = onDismiss,
            onAccept = { onSelect(entryIndex) }
        )
    }

}

@Composable
private fun PickerItem(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = MaterialTheme.paddings.giant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onSelect
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
            RadioChecker(
                isChecked = isSelected
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary.copy(0.1f),
            thickness = 1.dp,
        )
    }
}

@Composable
private fun Actions(
    onCancel: () -> Unit,
    onAccept: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.paddings.extraGiant,
                vertical = MaterialTheme.paddings.extraLarge
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            text = stringResource(id = R.string.cancel),
            backgroundColor = Color.Transparent,
            borderColor = MaterialTheme.colorScheme.secondary.copy(0.1f),
            onClick = onCancel
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.apply),
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = ColorTokens.Graphite,
            onClick = onAccept
        )
    }
}