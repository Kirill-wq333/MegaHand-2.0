package com.evothings.mhand.presentation.feature.catalog.ui.components.filters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.catalog.model.FilterValue
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.checkbox.CheckboxChecker
import com.evothings.mhand.presentation.feature.shared.radio.RadioChecker
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.list.toggleItem


@Composable
fun FilterAndSort(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    filters: Map<String, List<FilterValue>>,
    selected: Map<String, List<Int>>,
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

            FilterAndSortContent(
                modifier = Modifier
                    .padding(top = MaterialTheme.paddings.extraLarge),
                selected = selectedEntries,
                title = key,
                enableRadio = isSorting,
                entries = filterEntries,
                onExpandChange = { expanded ->
                    if (expanded) currentFilterExpanded = i
                },
                isExpanded = (i == currentFilterExpanded),
                onSelect = { entry ->
                    val oldList = selectedLocal[key]?.toMutableList() ?: mutableListOf()
                    val list = if (isSorting) mutableListOf() else oldList
                    list.toggleItem(entry)
                    selectedLocal.put(key, list)
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.paddings.extraGiant,
                    vertical = MaterialTheme.paddings.extraLarge
                )
        ) {
            Button(
                text = stringResource(R.string.cancel),
                borderColor = colorScheme.secondary.copy(.1f),
                textColor = colorScheme.secondary,
                onClick = onCancel,
                modifier = Modifier.weight(.5f)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.paddings.extraLarge))
            Button(
                text = stringResource(R.string.apply),
                backgroundColor = colorScheme.primary,
                textColor = colorScheme.secondary,
                onClick = { onApply(selectedLocal) },
                modifier = Modifier.weight(.5f)
            )
        }
    }
}


@Composable
private fun FilterAndSortContent(
    modifier: Modifier = Modifier,
    selected: List<Int>,
    entries: List<FilterValue>,
    enableRadio: Boolean = false,
    title: String,
    onSelect: (Int) -> Unit,
    isExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    val selectedLocal = remember(selected) {
        mutableStateListOf(*selected.toTypedArray())
    }

    var expanded by remember(isExpanded) {
        mutableStateOf(isExpanded)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        CardItem(
            text = title,
            isExpanded = expanded,
            onExpandChange = onExpandChange,
            content = {
                TextAndCheckBoxs(
                    selected = selectedLocal,
                    enableRadio = enableRadio,
                    entries = entries,
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

    }
}

@Composable
fun TextAndCheckBoxs(
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
            )
            .padding(
                horizontal = MaterialTheme.paddings.giant,
                vertical = MaterialTheme.paddings.extraLarge
            ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(entries){ index, item ->
            TextAndCheckBox(
                text = item.value,
                visibleChecker = (item.id in selected),
                isChecked = enableRadio,
                onClick = { onSelect(item.id) }
            )
            if (index < entries.size - 1){
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorScheme.secondary.copy(.05f))
                )
            }
        }
    }
}

@Composable
private fun CardItem(
    text: String,
    isExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    var expanded by remember(isExpanded) {
        mutableStateOf(isExpanded)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Item(
            text = text,
            isExpanded = expanded,
            onExpand = {
                expanded = !expanded
                onExpandChange(expanded)
            }
        )

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            content.invoke()
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorScheme.secondary.copy(.05f))
        )
    }

}
@Composable
fun Item(
    text: String,
    isExpanded: Boolean,
    onExpand: () -> Unit
){
    val iconTint = colorScheme.secondary.copy(
        alpha = if (isExpanded) 1.0f else 0.4f
    )
    val background =
        if (isExpanded) colorScheme.secondary.copy(.05f)
        else colorScheme.onSecondary

    val icon = remember(isExpanded) {
        if (isExpanded) R.drawable.ic_chevron_top
        else R.drawable.ic_chevron_bottom
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onExpand
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.paddings.extraGiant,
                    vertical = MaterialTheme.paddings.giant
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = colorScheme.secondary,
                style = MegahandTypography.headlineSmall
            )
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                tint = iconTint,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun TextAndCheckBox(
    text: String,
    visibleChecker: Boolean,
    isChecked: Boolean,
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
                .padding(horizontal = MaterialTheme.paddings.giant),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                color = colorScheme.secondary,
                style = MegahandTypography.bodyLarge,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.medium)
            )
            if (visibleChecker) {
                RadioChecker(
                    isChecked = isChecked
                )
            } else {
                CheckboxChecker(
                    isChecked = isChecked
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorScheme.secondary.copy(.05f))
        )
    }
}