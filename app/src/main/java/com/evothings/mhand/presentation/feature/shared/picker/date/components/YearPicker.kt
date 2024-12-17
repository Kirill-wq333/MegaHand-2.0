package com.evothings.mhand.presentation.feature.shared.picker.date.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.feature.shared.picker.date.model.Year
import com.evothings.mhand.presentation.feature.shared.picker.date.tools.PickerDateUtil

@Composable
fun YearPicker(
    selectedYear: Int,
    onSelect: (Int) -> Unit
) {

    val yearsList = remember(selectedYear) {
        PickerDateUtil.getYearsList(selectedYear)
    }

    val firstVisibleIndex = remember(yearsList) {
        val selectedYearIndex =
            yearsList.indexOf(
                Year(
                    value = selectedYear,
                    isSelected = true
                )
            )
        (selectedYearIndex - 6).coerceAtLeast(0)
    }

    val gridState = rememberLazyGridState(
        initialFirstVisibleItemIndex = firstVisibleIndex
    )

    LazyVerticalGrid(
        modifier = Modifier.height(252.dp),
        state = gridState,
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(yearsList) { item ->
            YearItem(
                year = item.value,
                isSelected = item.isSelected,
                onSelect = { onSelect(item.value) }
            )
        }
    }

}

@Composable
private fun YearItem(
    year: Int,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = MaterialTheme.shapes.small
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onSelect
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = year.toString(),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(
                    vertical = 6.dp,
                    horizontal = 9.dp
                )
        )
    }
}