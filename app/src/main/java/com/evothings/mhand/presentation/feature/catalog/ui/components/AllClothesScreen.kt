package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogCallback
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogUiState
import com.evothings.mhand.presentation.feature.home.ui.components.PreloadItem
import com.evothings.mhand.presentation.feature.shared.product.callback.ProductCardCallback
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun AllClothesScreen(
    uiState: CatalogUiState,
    callback: CatalogCallback
) {
    var filterBottomSheetExpanded by remember { mutableStateOf(false) }

    Content(
        subcategories = uiState.categories,
        products = uiState.products,
        gridScrollPosition = uiState.gridScrollPosition,
        prodCount = uiState.productsTotal,
        onClickFilter = {filterBottomSheetExpanded = true},
        callback = callback
    )
}

@Composable
private fun Content(
    products: LazyPagingItems<Product>,
    subcategories: List<ProductCategory>?,
    gridScrollPosition: Int,
    callback: CatalogCallback,
    prodCount: Int,
    onClickFilter: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = MaterialTheme.paddings.extraLarge),
        horizontalAlignment = Alignment.Start,
    ) {
        if (!subcategories.isNullOrEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small)
            ) {
                items(subcategories) { item ->
                    Button(
                        text = item.title
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.filters),
                color = colorScheme.secondary.copy(0.4f),
                style = MegahandTypography.bodyLarge,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.medium)
            )
            Text(
                text = stringResource(R.string.products_count, prodCount),
                color = colorScheme.secondary.copy(0.4f),
                style = MegahandTypography.bodyLarge,
                modifier = Modifier
                    .clickable { onClickFilter() }
                    .padding(
                        horizontal = MaterialTheme.paddings.extraLarge,
                        vertical = MaterialTheme.paddings.medium
                    )
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
        Products(
            products = products,
            callback = callback,
            initialScrollPosition = gridScrollPosition,
            onChangeScrollPosition = callback::updatePagingGridScrollPosition
        )
    }
}

@Composable
fun Products(
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

    val gridHeight = remember {
        val verticalPadding = 18 * 2
        val spacing = 9 * 2
        val productCardHeight = 400

        (verticalPadding + spacing + productCardHeight * 2).dp
    }


    LazyVerticalGrid(
        modifier = Modifier
            .height(gridHeight),
        columns = GridCells.Fixed(2),
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraLarge),
    ) {
        items(
            count = products.itemCount,
            key = products.itemKey { product -> product.id },
            contentType = products.itemContentType { product -> product::class.simpleName }
        ) { index ->
            val item = products.get(index)
            item?.let { i ->
                PreloadItem(
                    model = i,
                    callback = callback
                )
            }
        }
    }

}

@Composable
fun Button(
    text: String
){
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = colorScheme.primary, shape = MegahandShapes.medium)
    ){
        Text(
            text = text,
            color = colorScheme.secondary,
            style = MegahandTypography.labelLarge,
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.paddings.giant,
                    vertical = MaterialTheme.paddings.extraLarge
                )
        )
    }
}

