package com.evothings.mhand.presentation.feature.catalog.ui.components.filters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.catalog.model.FilterValue
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.checkbox.CheckboxChecker
import com.evothings.mhand.presentation.feature.shared.radio.RadioChecker
import com.evothings.mhand.presentation.feature.shared.text.saver.BooleanSaver
import com.evothings.mhand.presentation.theme.MegahandTheme
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
            .fillMaxWidth()
            .background(color = colorScheme.onSecondary)
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
    enableRadio: Boolean,
    title: String,
    onSelect: (Int) -> Unit
) {
    val selectedLocal = remember(selected) {
        mutableStateListOf(*selected.toTypedArray())
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        CardItem(
            text = title,
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
    modifier: Modifier = Modifier,
    selected: List<Int>,
    entries: List<FilterValue>,
    enableRadio: Boolean,
    onSelect: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.paddings.giant,
                vertical = MaterialTheme.paddings.extraLarge
            ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
    ) {
        items(entries){ item ->
            TextAndCheckBox(
                text = item.value,
                visibleChecker = (item.id in selected),
                isChecked = enableRadio,
                onClick = { onSelect(item.id) }
            )
        }
    }
}

@Composable
private fun CardItem(
    modifier: Modifier = Modifier,
    text: String,
    onExpandStateChange: (Boolean) -> Unit = {},
    content: @Composable () -> Unit
) {
    var isExpanded by rememberSaveable(Unit, BooleanSaver) { mutableStateOf(false) }

    val iconTint = MaterialTheme.colorScheme.secondary.copy(
        alpha = if (isExpanded) 1.0f else 0.4f
    )
    val background =
        if (isExpanded) colorScheme.secondary.copy(.05f)
        else colorScheme.onSecondary

    val icon = remember(isExpanded) {
        if (isExpanded) R.drawable.ic_chevron_top
        else R.drawable.ic_chevron_bottom
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
                .clickable { isExpanded = !isExpanded; onExpandStateChange(isExpanded) },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.paddings.extraGiant,
                        vertical = MaterialTheme.paddings.giant
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = text,
                    color = colorScheme.secondary,
                    style = MegahandTypography.headlineSmall
                )
                IconButton(
                    icon = ImageVector.vectorResource(icon),
                    iconPadding = 0.dp,
                    tint = iconTint,
                    onClick = { }
                )
            }
        }
        AnimatedVisibility(
            visible = isExpanded,
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
fun TextAndCheckBox(
    modifier: Modifier = Modifier,
    text: String,
    visibleChecker: Boolean,
    isChecked: Boolean,
    onClick: () -> Unit
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.paddings.giant)
                .clickable { onClick() },
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
                CheckboxChecker(
                    isChecked = isChecked,
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.paddings.medium,
                            vertical = MaterialTheme.paddings.large
                        )
                )
            } else {
                RadioChecker(
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