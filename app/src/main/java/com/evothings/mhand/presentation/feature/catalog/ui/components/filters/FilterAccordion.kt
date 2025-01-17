package com.evothings.mhand.presentation.feature.catalog.ui.components.filters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.catalog.model.FilterValue
import com.evothings.mhand.presentation.feature.shared.accordion.Accordion
import com.evothings.mhand.presentation.feature.shared.checkbox.CheckboxChecker
import com.evothings.mhand.presentation.feature.shared.radio.RadioChecker
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.utils.list.toggleItem

@Composable
fun FilterAccordion(
    title: String,
    selected: List<Int>,
    entries: List<FilterValue>,
    isExpanded: Boolean,
    enableRadio: Boolean = false,
    onExpandChange: (Boolean) -> Unit,
    onSelect: (Int) -> Unit
) {

    val selectedLocal = remember(selected) {
        mutableStateListOf(*selected.toTypedArray())
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Accordion(
            title = title,
            isExpanded = isExpanded,
            onExpandChange = onExpandChange,
            content = {
                FiltersList(
                    selected = selectedLocal,
                    entries = entries,
                    enableRadio = enableRadio,
                    onSelect = { id ->
                        if (!enableRadio) {
                            selectedLocal.toggleItem(id)
                        } else {
                            selectedLocal.clear()
                            selectedLocal.add(id)
                        }
                        onSelect(id)
                    }
                )
            }
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondary.copy(0.05f)
        )
    }
}

@Composable
private fun FiltersList(
    selected: List<Int>,
    entries: List<FilterValue>,
    enableRadio: Boolean,
    onSelect: (Int) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(
                min = 100.dp,
                max = 280.dp
            ),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.paddings.giant
        )
    ) {
        items(entries) { item ->
            FilterItem(
                text = item.value,
                isSelected = (item.id in selected),
                radio = enableRadio,
                onClick = { onSelect(item.id) }
            )
        }
    }
}

@Composable
private fun FilterItem(
    text: String,
    isSelected: Boolean,
    radio: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.paddings.extraLarge),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
            if (radio) {
                RadioChecker(
                    isChecked = isSelected
                )
            } else {
                CheckboxChecker(
                    isChecked = isSelected
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondary.copy(0.05f)
        )
    }
}