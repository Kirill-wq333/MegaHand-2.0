package com.evothings.mhand.presentation.feature.catalog.ui.components.filters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.evothings.mhand.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.domain.feature.catalog.model.FilterValue
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.utils.list.toggleItem


@Composable
fun FiltersModal(
    modifier: Modifier = Modifier,
    filters: Map<String, List<FilterValue>>,
    selected: Map<String, List<Int>>,
    onDismiss: () -> Unit,
    onApply: (Map<String, List<Int>>) -> Unit
) {
    val selectedLocal = remember(selected) {
        val selectedAsPairs = selected.map { it.key to it.value }
        mutableStateMapOf(*selectedAsPairs.toTypedArray())
    }

    var currentFilterExpanded by remember {
        mutableIntStateOf(-1)
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        repeat(filters.keys.size) { i ->
            val key = remember { filters.keys.elementAt(i) }
            val isSorting = remember { key == "Сортировка" }
            val selectedEntries = remember(selectedLocal) { selectedLocal[key] ?: listOf(0) }
            val filterEntries = remember { filters[key] ?: listOf() }

            FilterAccordion(
                title = key,
                selected = selectedEntries,
                enableRadio = isSorting,
                isExpanded = (i == currentFilterExpanded),
                entries = filterEntries,
                onExpandChange = { expanded ->
                    if (expanded) currentFilterExpanded = i
                },
                onSelect = { entry ->
                    val oldList = selectedLocal[key]?.toMutableList() ?: mutableListOf()
                    val list = if (isSorting) mutableListOf() else oldList
                    list.toggleItem(entry)
                    selectedLocal.put(key, list)
                }
            )
        }
        Actions(
            onCancel = onDismiss,
            onApply = { onApply(selectedLocal) }
        )
    }
}

@Composable
private fun Actions(
    onCancel: () -> Unit,
    onApply: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.paddings.extraLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.cancel),
            backgroundColor = Color.Transparent,
            borderColor = MaterialTheme.colorScheme.secondary.copy(0.1f),
            onClick = onCancel
        )
        Spacer(
            modifier = Modifier
                .width(MaterialTheme.paddings.medium)
        )
        Button(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.apply),
            textColor = ColorTokens.Graphite,
            backgroundColor = MaterialTheme.colorScheme.primary,
            onClick = onApply
        )
    }
}

//val filtersMap: Map<String, List<String>> = mapOf(
//    "Сортировка" to listOf(
//        "Сначала дешевые",
//        "Сначала новые",
//    ),
//    "Цвет" to listOf(
//        "Все",
//        "Красный",
//        "Оранжевый",
//        "Черный",
//        "Белый",
//        "Желтый",
//        "Зеленый",
//        "Голубой",
//        "Бурый",
//        "Другой",
//        "Лимоновый",
//    ),
//    "Материал" to listOf(
//        "Все",
//        "Хлопок",
//        "Лен",
//        "Шерсть",
//        "Синтетика",
//    ),
//    "Размер" to listOf(
//        "Все",
//        "XXS",
//        "XS",
//        "S",
//        "M",
//        "L",
//        "XL",
//        "XXL",
//        "XXXL",
//    ),
//    "Качество" to listOf(
//        "Любое",
//        "Низкое",
//        "Среднее",
//        "Высокое",
//    ),
//)

@Preview
@Composable
private fun FiltersModalPreview() {
    MegahandTheme {
        Surface {
            FiltersModal(
                filters = mapOf(),
                modifier = Modifier,
                onDismiss = { },
                onApply = {},
                selected = mapOf()
            )
        }
    }
}