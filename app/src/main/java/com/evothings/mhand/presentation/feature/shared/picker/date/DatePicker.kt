package com.evothings.mhand.presentation.feature.shared.picker.date

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import com.evothings.mhand.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.picker.date.components.CalendarGrid
import com.evothings.mhand.presentation.feature.shared.picker.date.components.WeekdaysRow
import com.evothings.mhand.presentation.feature.shared.picker.date.components.YearPicker
import com.evothings.mhand.presentation.feature.shared.picker.date.tools.PickerDateUtil
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers
import java.time.format.TextStyle
import java.util.Locale

@Preview
@Composable
private fun DatePickerPreview() {
    MegahandTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorScheme.background)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            DatePicker(
                dateToSet = 1727105298309,
                onConfirmDate = {},
                onHide = {}
            )
        }
    }
}

@Composable
fun DatePicker(
    dateToSet: Long,
    onConfirmDate: (Long) -> Unit,
    onHide: () -> Unit
) {

    var timestamp by remember(dateToSet) { mutableLongStateOf(dateToSet) }

    val localDate = remember(timestamp) {
        PickerDateUtil.timestampToLocalDate(timestamp)
    }

    val monthName =
        remember(localDate) {
            val monthLowercase = localDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
            monthLowercase.replaceFirstChar { it.uppercase() }
        }

    var isYearPickerExpanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${localDate.dayOfMonth} $monthName, ${localDate.year}",
            style = typography.headlineMedium
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CurrentMonth(
                month = monthName,
                year = localDate.year,
                isExpanded = isYearPickerExpanded,
                onExpand = {
                    isYearPickerExpanded = !isYearPickerExpanded
                }
            )
            if (!isYearPickerExpanded) {
                ShiftMonthButtons(
                    onClickLeft = {
                        timestamp =
                            PickerDateUtil.setMonth(timestamp, pushForward = false)
                    },
                    onClickRight = {
                        timestamp =
                            PickerDateUtil.setMonth(timestamp, pushForward = true)
                    }
                )
            }
        }
        if (!isYearPickerExpanded) {
            WeekdaysRow()
            CalendarGrid(
                dateTimestamp = timestamp,
                onChooseDay = { newTimestamp ->
                    timestamp = newTimestamp
                }
            )
        } else {
            YearPicker(
                selectedYear = localDate.year,
                onSelect = {
                    timestamp = PickerDateUtil.setYear(timestamp, it)
                }
            )
        }
        PickerActionButtons(
            onClickHide = onHide,
            onProceed = { onConfirmDate(timestamp) }
        )
    }

}

@Composable
private fun CurrentMonth(
    month: String,
    year: Int,
    isExpanded: Boolean,
    onExpand: () -> Unit
) {
    Row(
        modifier = Modifier
            .alpha(
                alpha = if (isExpanded) 0.4f else 1.0f
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onExpand
            ),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$month, $year",
            style = typography.bodyLarge
        )
        Icon(
            imageVector = ImageVector.vectorResource(
                id = if (isExpanded) R.drawable.ic_chevron_top else R.drawable.ic_chevron_bottom
            ),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun ShiftMonthButtons(
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_chevron_left),
            tint = colorScheme.secondary,
            backgroundColor = Color.Transparent,
            borderColor = Color.Transparent,
            onClick = onClickLeft
        )
        IconButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_chevron_right),
            tint = colorScheme.secondary,
            backgroundColor = Color.Transparent,
            borderColor = Color.Transparent,
            onClick = onClickRight
        )
    }
}

@Composable
private fun PickerActionButtons(
    onClickHide: () -> Unit,
    onProceed: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.cancel),
            borderColor = colorScheme.secondary.copy(alpha = 0.1f),
            textColor = colorScheme.secondary,
            onClick = onClickHide
        )
        Spacer(
            modifier = Modifier
                .width(MaterialTheme.spacers.medium)
        )
        Button(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.save),
            backgroundColor = colorScheme.primary,
            textColor = ColorTokens.Graphite,
            onClick = onProceed
        )
    }
}