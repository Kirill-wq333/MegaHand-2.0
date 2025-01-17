package com.evothings.mhand.presentation.feature.favourites.ui

//import android.widget.Toast
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.evothings.domain.feature.catalog.model.ProductCategory
//import com.evothings.domain.feature.product.model.Product
//import com.evothings.mhand.R
//import com.evothings.mhand.presentation.feature.catalog.ui.components.ProductListGrid
//import com.evothings.mhand.presentation.feature.favourites.viewmodel.FavouritesContract
//import com.evothings.mhand.presentation.feature.favourites.viewmodel.FavouritesViewModel
//import com.evothings.mhand.presentation.feature.shared.MLazyRow
//import com.evothings.mhand.presentation.feature.shared.button.Chip
//import com.evothings.mhand.presentation.feature.shared.header.ui.HeaderProvider
//import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
//import com.evothings.mhand.presentation.feature.shared.product.callback.ProductCardCallback
//import com.evothings.mhand.presentation.feature.shared.pullToRefresh.PullRefreshLayout
//import com.evothings.mhand.presentation.feature.shared.screen.EmptyListScreen
//import com.evothings.mhand.presentation.feature.shared.screen.ServerErrorScreen
//import com.evothings.mhand.presentation.theme.spacers
//
//private interface FavouritesCallback : ProductCardCallback {
//    fun refresh()
//    fun selectCategory(category: ProductCategory)
//    fun clearList()
//    fun openCatalog()
//}
//
//data class FavouritesUiState(
//    val products: List<Product>,
//    val categories: List<ProductCategory>,
//    val selectedCategory: ProductCategory
//)
//
//@Composable
//fun FavouritesScreen(
//    vm: FavouritesViewModel,
//    openProductInfoScreen: (Int) -> Unit,
//    openCatalog: () -> Unit
//) {
//
//    val state by vm.state.collectAsStateWithLifecycle()
//
//    val products by vm.products.collectAsState()
//    val categories by vm.categories.collectAsState()
//    val selectedCategory by vm.selectedCategory.collectAsState()
//
//    LaunchedEffect(state) {
//        if (state is FavouritesContract.State.Loading) {
//            vm.handleEvent(FavouritesContract.Event.LoadItems)
//        }
//    }
//
//    val context = LocalContext.current
//    LaunchedEffect(vm.effect) {
//        vm.effect.collect {
//            if (it is FavouritesContract.Effect.ShowToast) {
//                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    val callback = object : FavouritesCallback {
//
//        override fun refresh() =
//            vm.handleEvent(FavouritesContract.Event.Refresh)
//
//        override fun selectCategory(category: ProductCategory) =
//            vm.handleEvent(FavouritesContract.Event.SelectCategory(category))
//
//        override fun clearList() =
//            vm.handleEvent(FavouritesContract.Event.ClearList)
//
//        override fun openCatalog() = openCatalog()
//
//        override fun openProductDetailScreen(id: Int) = openProductInfoScreen(id)
//
//        override fun addToCart(id: Int) =
//            vm.handleEvent(FavouritesContract.Event.AddProductToCart(id))
//
//        override fun toggleFavourite(id: Int) =
//            vm.handleEvent(FavouritesContract.Event.RemoveFromFavourite(id))
//
//    }
//
//    FavouritesContent(
//        state = state,
//        uiState = FavouritesUiState(
//            products = products,
//            categories = categories,
//            selectedCategory = selectedCategory
//        ),
//        callback = callback
//    )
//
//}
//
//@Composable
//private fun FavouritesContent(
//    state: FavouritesContract.State,
//    uiState: FavouritesUiState,
//    callback: FavouritesCallback
//) {
//
//    val enableCardBalance =
//        remember(state) { state !is FavouritesContract.State.ListIsEmpty }
//
//    HeaderProvider(
//        screenTitle = stringResource(R.string.favourites_screen_title),
//        enableBackButton = false,
//        enableMapIconButton = false,
//        enableCardBalance = enableCardBalance,
//        onBack = {}
//    ) { headerPadding ->
//
//        Box(
//            modifier = Modifier.padding(headerPadding)
//        ) {
//            when(state) {
//                is FavouritesContract.State.Loading -> {
//                    LoadingScreen()
//                }
//                is FavouritesContract.State.Loaded -> {
//                    PullRefreshLayout(onRefresh = callback::refresh) {
//                        Content(
//                            categories = uiState.categories,
//                            selectedCategory = uiState.selectedCategory,
//                            products = uiState.products,
//                            callback = callback
//                        )
//                    }
//                }
//                is FavouritesContract.State.ListIsEmpty -> {
//                    EmptyListScreen(
//                        text = stringResource(R.string.favourites_list_empty),
//                        onClickOpenCatalog = callback::openCatalog
//                    )
//                }
//                is FavouritesContract.State.ServerError -> {
//                    ServerErrorScreen(
//                        onRefresh = callback::refresh
//                    )
//                }
//            }
//        }
//
//    }
//
//}
//
//@Composable
//private fun Content(
//    categories: List<ProductCategory>,
//    selectedCategory: ProductCategory,
//    products: List<Product>,
//    callback: FavouritesCallback
//) {
//    Column {
//        MLazyRow(
//            itemsList = categories,
//            elementsSpacing = 8.dp,
//            listHorizontalPadding = 12.dp
//        ) { item, _ ->
//            Chip(
//                text = item.title,
//                enabled = (item == selectedCategory),
//                onClick = { callback.selectCategory(item) }
//            )
//        }
//        Spacer(
//            modifier = Modifier
//                .height(MaterialTheme.spacers.medium)
//        )
//        Column(
//            modifier = Modifier
//                .padding(horizontal = MaterialTheme.spacers.medium)
//        ) {
//            Text(
//                text = stringResource(R.string.clear_favourites_list),
//                style = MaterialTheme.typography.bodyLarge,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier
//                    .padding(6.dp)
//                    .clickable(
//                        interactionSource = remember { MutableInteractionSource() },
//                        indication = null,
//                        onClick = callback::clearList
//                    )
//            )
//            Spacer(
//                modifier = Modifier
//                    .height(MaterialTheme.spacers.medium)
//            )
//            ProductListGrid(
//                products = products,
//                callback = callback
//            )
//        }
//
//    }
//}