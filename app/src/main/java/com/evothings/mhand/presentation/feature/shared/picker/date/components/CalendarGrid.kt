package com.evothings.mhand.presentation.feature.shared.picker.date.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.feature.shared.picker.date.tools.PickerDateUtil
import com.evothings.mhand.presentation.feature.shared.picker.date.tools.PickerDaysCalculator
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens

@Composable
fun CalendarGrid(
    dateTimestamp: Long,
    onChooseDay: (Long) -> Unit
) {

    val daysList = remember(dateTimestamp) {
        val calculator = PickerDaysCalculator(dateTimestamp)
        calculator.getDays()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7)
    ) {
        items(daysList) { item ->
            DayItem(
                number = item.number,
                isActive = item.isActive,
                isSelected = item.isSelected,
                onSelect = {
                    val selectedDayMillis = PickerDateUtil.setDay(dateTimestamp, item.number)
                    onChooseDay(selectedDayMillis)
                }
            )
        }
    }

}

@Composable
fun WeekdaysRow() {
    val ruWeekdaysList = remember {
        listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(ruWeekdaysList.size) { i ->
            Text(
                text = ruWeekdaysList[i],
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary.copy(0.4f)
            )
        }
    }
}

@Composable
private fun DayItem(
    number: Int,
    isActive: Boolean,
    isSelected: Boolean,
    onSelect: () -> Unit
) {

    val backgroundColor =
        if (isSelected && isActive) MaterialTheme.colorScheme.primary else Color.Transparent

    val numberColor =
        if (isSelected && isActive) ColorTokens.Graphite else MaterialTheme.colorScheme.secondary

    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.small
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { if (isActive) onSelect() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = numberColor.copy(alpha = if (isActive) 1.0f else .25f),
            modifier = Modifier.padding(
                vertical = 6.dp,
                horizontal = 9.dp
            )
        )
    }
}