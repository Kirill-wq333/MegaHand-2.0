package com.evothings.mhand.presentation.feature.cart.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.cart.ui.CartCallback
import com.evothings.mhand.presentation.feature.cart.ui.components.productComponents.Cart
import com.evothings.mhand.presentation.feature.shared.checkbox.Checkbox
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

fun LazyListScope.productsList(
    products: List<Product>,
    selectionList: List<Int>,
    isSelectAll: Boolean,
    callback: CartCallback,
    onSelect: (Int) -> Unit,
    onSelectAll: () -> Unit,
    onClearSelection: () -> Unit
) {

    item {
        CartSelectionOptions(
            selectAllChecked = isSelectAll,
            onClear = onClearSelection,
            onCheckSelectAll = onSelectAll
        )
    }

    item {
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
    }

    items(
        items = products,
        key = { it.id }
    ) { product ->
        Cart(
            model = product,
            isSelected = (product.id in selectionList),
            onSelect = { onSelect(product.id) },
            onClick = { callback.onClickProduct(product.id) },
            toggleFavourite = { callback.toggleFavourite(product.id) },
            removeFromCart = { callback.removeFromCart(product.id) }
        )
    }

}

@Composable
fun CartSelectionOptions(
    modifier: Modifier = Modifier,
    selectAllChecked: Boolean,
    onCheckSelectAll: () -> Unit,
    onClear: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.paddings.extraLarge),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Checkbox(
            title = stringResource(R.string.select_all),
            isChecked = selectAllChecked,
            onCheck = onCheckSelectAll
        )
        Text(
            text = stringResource(R.string.clear_all),
            color = colorScheme.error,
            style = MegahandTypography.bodyLarge,
        )
    }
}