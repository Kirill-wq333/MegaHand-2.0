package com.evothings.mhand.presentation.feature.catalog.ui.components

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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogCallback
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogUiState
import com.evothings.mhand.presentation.feature.catalog.ui.components.filters.FilterAndSort
import com.evothings.mhand.presentation.feature.catalog.viewmodel.CatalogContract
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Chip
import com.evothings.mhand.presentation.feature.shared.header.ui.HeaderProvider
import com.evothings.mhand.presentation.feature.shared.loading.LoadingIndicator
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.product.ProductItem
import com.evothings.mhand.presentation.feature.shared.product.callback.ProductCardCallback
import com.evothings.mhand.presentation.feature.shared.pullToRefresh.PullRefreshLayout
import com.evothings.mhand.presentation.feature.shared.screen.ServerErrorScreen
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers


@Composable
fun AllClothesScreen(
    state: CatalogContract.State,
    uiState: CatalogUiState,
    callback: CatalogCallback
) {
    var filterBottomSheetExpanded by remember { mutableStateOf(false) }

    ClothesContent(
        state = state,
        products = uiState.products,
        productsCount = uiState.productsTotal,
        selectedSubcategory = uiState.selectedSubcategory,
        initialGridScrollPosition = uiState.gridScrollPosition,
        openFilterBottomSheet = { filterBottomSheetExpanded = true },
        callback = callback,
    )


    if (filterBottomSheetExpanded) {
        MhandModalBottomSheet(
            onDismissRequest = { filterBottomSheetExpanded = false }
        ) { hide ->
            FilterAndSort(
                filters = uiState.filters,
                selected = uiState.appliedFilters,
                onCancel = { hide(); filterBottomSheetExpanded = false },
                onApply = {
                    callback.applyFilters(it); hide()
                }
            )
        }
    }
}

@Composable
private fun ClothesContent(
    state: CatalogContract.State,
    products: LazyPagingItems<Product>,
    productsCount: Int,
    initialGridScrollPosition: Int,
    selectedSubcategory: ProductCategory?,
    openFilterBottomSheet: () -> Unit,
    callback: CatalogCallback,
){
    val screenTitle = remember(state) {
        if (state is CatalogContract.State.SubcategoryProducts) state.category.title else "Вся одежда"
    }

    HeaderProvider(
        screenTitle = screenTitle,
        enableMapIconButton = false,
        turnButtonVisible = true,
        onBack = callback::resetState
    ) { headerPadding ->
        PullRefreshLayout(
            onRefresh = {
                if (state is CatalogContract.State.SubcategoryProducts) {
                    callback.refreshSubcategoryProducts(state.category)
                }
            }
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(headerPadding),
                topBar = {
                    if (state is CatalogContract.State.SubcategoryProducts) {
                        Content(
                            subcategories = state.subcategories,
                            selectedName = selectedSubcategory?.title.orEmpty(),
                            prodCount = productsCount,
                            onClickFilter = openFilterBottomSheet,
                            onClickSubcategory = callback::selectSubcategory,
                        )
                    }
                }
            ) { scaffoldPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding),
                    contentAlignment = Alignment.Center
                ) {
                    when (state) {
                        is CatalogContract.State.CategoryProductsListLoading -> {
                            LoadingScreen()
                        }

                        is CatalogContract.State.CategoryProductsServerError -> {
                            ServerErrorScreen(
                                onRefresh = { callback.resetState() }
                            )
                        }

                        is CatalogContract.State.SubcategoryProducts -> {
                            Products(
                                products = products,
                                initialScrollPosition = initialGridScrollPosition,
                                onChangeScrollPosition = callback::updatePagingGridScrollPosition,
                                callback = callback
                            )
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
private fun Content(
    subcategories: List<ProductCategory>?,
    prodCount: Int,
    onClickFilter: () -> Unit,
    selectedName: String,
    onClickSubcategory: (ProductCategory) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.paddings.extraLarge),
        horizontalAlignment = Alignment.Start,
    ) {
        if (!subcategories.isNullOrEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small)
            ) {
                items(subcategories) { item ->
                    Chip(
                        text = item.title,
                        enabled = (item.title == selectedName),
                        onClick = { onClickSubcategory(item) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        FilterAndSorting(
            onClickFilter = onClickFilter,
            prodCount = prodCount
        )
    }
}

@Composable
fun FilterAndSorting(
    onClickFilter: () -> Unit,
    prodCount: Int
) {
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
                .clickable { onClickFilter() }
        )
        Text(
            text = stringResource(R.string.products_count, prodCount),
            color = colorScheme.secondary.copy(0.4f),
            style = MegahandTypography.bodyLarge,
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.paddings.extraLarge,
                    vertical = MaterialTheme.paddings.medium
                )
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

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        state = gridState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraLarge),
    ) {
        items(
            count = products.itemCount,
            contentType = products.itemContentType { product -> product::class.simpleName }
        ) { index ->
            val item = products[index]
            item?.let { i ->
                ProductItem(
                    model = i,
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

