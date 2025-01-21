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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@Preview
@Composable
private fun FilterPreview() {
    MegahandTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorScheme.onSecondary.copy(.3f)),
            contentAlignment = Alignment.BottomEnd
        ) {
            Content(
                onCancel = {},
                onApply = {}
            )
        }
    }
}

@Composable
fun FContent(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    MhandModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = {
            Content(
                onCancel = {},
                onApply = {}
            )
        }
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    onApply: () -> Unit,
    onCancel: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorScheme.onSecondary)
    ) {
        FilterAndSortContent()
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
            Button(
                text = stringResource(R.string.apply),
                backgroundColor = colorScheme.primary,
                textColor = colorScheme.secondary,
                onClick = onApply,
                modifier = Modifier.weight(.5f)
            )
        }
    }
}


@Composable
private fun FilterAndSortContent(
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CardItem(
            text = stringResource(R.string.sort),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.paddings.giant,
                            vertical = MaterialTheme.paddings.extraLarge
                        ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
                ) {
                    TextAndCheckBox(
                        text = "Сначала дешевые",
                        visibleChecker = false,
                    )
                    TextAndCheckBox(
                        text = "Сначала новые",
                        visibleChecker = false
                    )
                }
            }

        )
        CardItem(
            text = stringResource(R.string.color_product),
            content = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.paddings.giant,
                            vertical = MaterialTheme.paddings.extraLarge
                        ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
                ) {
                    items(10) {
                        TextAndCheckBox(
                            text = "Черный",
                            visibleChecker = true
                        )
                    }
                }
            }
        )
        CardItem(
            text = stringResource(R.string.material),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.paddings.giant,
                            vertical = MaterialTheme.paddings.extraLarge
                        ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
                ) {

                    TextAndCheckBox(
                        text = "Все",
                        visibleChecker = true
                    )
                    TextAndCheckBox(
                        text = "Хлопок",
                        visibleChecker = true
                    )
                    TextAndCheckBox(
                        text = "Лен",
                        visibleChecker = true
                    )
                    TextAndCheckBox(
                        text = "Шерсть",
                        visibleChecker = true
                    )
                    TextAndCheckBox(
                        text = "Синтетика",
                        visibleChecker = true
                    )
                }
            }
        )
        CardItem(
            text = stringResource(R.string.size_product),
            content = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.paddings.giant,
                            vertical = MaterialTheme.paddings.extraLarge
                        ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
                ) {
                    items(10){
                        TextAndCheckBox(
                            text = "Все",
                            visibleChecker = true
                        )
                    }
                }
            }
        )
        CardItem(
            text = stringResource(R.string.quality_product),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.paddings.giant,
                            vertical = MaterialTheme.paddings.extraLarge
                        ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
                ) {
                    TextAndCheckBox(
                        text = "Все",
                        visibleChecker = true
                    )
                    TextAndCheckBox(
                        text = "Высокое",
                        visibleChecker = true
                    )
                    TextAndCheckBox(
                        text = "Среднее",
                        visibleChecker = true
                    )
                    TextAndCheckBox(
                        text = "Низкое",
                        visibleChecker = true
                    )
                }
            }
        )

    }
}


@Composable
private fun CardItem(
    modifier: Modifier = Modifier,
    text: String,
    onExpandStateChange: (Boolean) -> Unit = {},
    content: @Composable (ColumnScope) -> Unit
) {
    var isExpanded by rememberSaveable(Unit, BooleanSaver) { mutableStateOf(false) }

    val background =
        if (isExpanded) colorScheme.secondary.copy(.05f)
        else colorScheme.onSecondary

    val icon = remember(isExpanded) {
        if (isExpanded) R.drawable.ic_chevron_top
        else R.drawable.ic_chevron_bottom
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Box(
                modifier = Modifier
                .fillMaxWidth()
                .background(background)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.paddings.extraGiant,
                            vertical = MaterialTheme.paddings.giant
                        )
                        .clickable { isExpanded = !isExpanded; onExpandStateChange(isExpanded) },
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
                        onClick = { }
                    )
                }
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column {
                    content.invoke(this)
                }
            }
        }
    }
}

@Composable
fun TextAndCheckBox(
    modifier: Modifier = Modifier,
    text: String,
    visibleChecker: Boolean
) {

    var isChecked by rememberSaveable(Unit, BooleanSaver) { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.paddings.giant)
            .clickable { isChecked = !isChecked },
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
        } else{
            RadioChecker(
                isChecked = isChecked
            )
        }
    }
}