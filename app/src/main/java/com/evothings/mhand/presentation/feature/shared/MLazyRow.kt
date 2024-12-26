package com.evothings.mhand.presentation.feature.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> MLazyRow(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    itemsList: List<T>,
    elementsSpacing: Dp = 15.dp,
    listHorizontalPadding: Dp = elementsSpacing,
    itemContent: @Composable (T, Int) -> Unit
) {
    LazyRow(
        state = state,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = listHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(elementsSpacing)
    ) {
        itemsIndexed(itemsList) { index, item ->
            itemContent.invoke(item, index)
        }
    }
}