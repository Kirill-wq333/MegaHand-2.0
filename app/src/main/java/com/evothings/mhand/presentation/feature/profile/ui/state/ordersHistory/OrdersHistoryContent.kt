package com.evothings.mhand.presentation.feature.profile.ui.state.ordersHistory

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.evothings.domain.feature.profile.model.Order
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.text.DatePickerTextField
import com.evothings.mhand.presentation.feature.shared.text.SearchField
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun OrdersHistoryContent(
    orders: List<Order>,
    query: String,
    dateFilter: String,
    onChangeSearchQuery: (String) -> Unit,
    onChangeDateFilter: (String) -> Unit,
    onClickProduct: (Int) -> Unit,
    onCopyOrderTrack: (String) -> Unit,
    onContinueCheckout: (orderId: String) -> Unit,
    onClickPayOrder: (paymentLink: String?) -> Unit
) {

    LazyColumn {

        item {
            SearchField(
                query = query,
                placeholder = stringResource(R.string.order_search_placeholder),
                onValueChange = onChangeSearchQuery
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        }

        item {
            DatePickerTextField(
                date = dateFilter,
                placeholder = stringResource(R.string.order_date_search_placeholder),
                onDateChange = onChangeDateFilter
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        }

        items(
            items = orders,
            key = { it.id }
        ) { order ->
            OrderItem(
                model = order,
                onClickProduct = onClickProduct,
                onCopyTrack = onCopyOrderTrack,
                onClickPayOrder = { onClickPayOrder(order.paymentUrl) },
                onContinueCheckout = { onContinueCheckout(order.id) }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        }

    }

}