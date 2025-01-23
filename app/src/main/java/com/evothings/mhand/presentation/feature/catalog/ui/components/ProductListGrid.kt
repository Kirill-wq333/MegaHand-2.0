package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.presentation.feature.shared.loading.LoadingIndicator
import com.evothings.mhand.presentation.feature.shared.product.CatalogProductItem
import com.evothings.mhand.presentation.feature.shared.product.callback.ProductCardCallback
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    products: LazyPagingItems<Product>,
    initialScrollPosition: Int,
    callback: ProductCardCallback,
    onChangeScrollPosition: (Int) -> Unit
) {

    val isLoading = remember {
        derivedStateOf {
            products.loadState.append is LoadState.Loading
                    || products.loadState.refresh is LoadState.Loading
        }
    }

    val gridState = rememberLazyGridState(
        initialFirstVisibleItemIndex = initialScrollPosition
    )

    LaunchedEffect(gridState.isScrollInProgress) {
        if (!gridState.isScrollInProgress) {
            onChangeScrollPosition(gridState.firstVisibleItemIndex)
        }
    }

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        state = gridState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraLarge)
    ) {

        items(
            count = products.itemCount,
            key = products.itemKey { product -> product.id },
            contentType = products.itemContentType { product -> product::class.simpleName }
        ) {  index ->
            val item = products.get(index)

            item?.let { product ->
                CatalogProductItem(
                    model = product,
                    callback = callback
                )
            }
        }

        if (isLoading.value) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator()
                }
            }
        }

    }
}

@Composable
fun ProductListGrid(
    modifier: Modifier = Modifier,
    products: List<Product>,
    callback: ProductCardCallback
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraLarge)
    ) {
        items(
            items = products,
            key = { it.id }
        ) { item ->
            CatalogProductItem(
                model = item,
                callback = callback
            )
        }
    }
}