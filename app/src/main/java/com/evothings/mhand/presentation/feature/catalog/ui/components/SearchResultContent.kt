package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogCallback
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogUiState
import com.evothings.mhand.presentation.feature.catalog.ui.components.filters.FilterAndSort
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun SearchResultContent(
    uiState: CatalogUiState,
    callback: CatalogCallback
) {

    var filterBottomSheetExpanded by remember { mutableStateOf(false) }

    ResultsList(
        products = uiState.products,
        productsCount = uiState.productsTotal,
        gridScrollPosition = uiState.gridScrollPosition,
        openFiltersModal = { filterBottomSheetExpanded = true },
        callback = callback
    )

    if (filterBottomSheetExpanded) {
        MhandModalBottomSheet(
            onDismissRequest = { filterBottomSheetExpanded = false }
        ) {  hide ->
            FilterAndSort(
                filters = uiState.filters,
                selected = uiState.appliedFilters,
                onCancel = hide,
                onApply = { map ->
                    callback.applyFilters(map); hide()
                }
            )
        }
    }

}

@Composable
private fun ResultsList(
    products: LazyPagingItems<Product>,
    productsCount: Int,
    gridScrollPosition: Int,
    openFiltersModal: () -> Unit,
    callback: CatalogCallback
) {

    Column {
        FilterAndSorting(
            prodCount = productsCount,
            onClickFilter = openFiltersModal
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.medium)
        )
        Products(
            products = products,
            initialScrollPosition = gridScrollPosition,
            callback = callback,
            onChangeScrollPosition = callback::updatePagingGridScrollPosition
        )
    }
}