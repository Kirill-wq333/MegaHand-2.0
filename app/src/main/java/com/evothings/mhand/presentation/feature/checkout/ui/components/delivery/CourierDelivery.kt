package com.evothings.mhand.presentation.feature.checkout.ui.components.delivery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.evothings.domain.feature.address.model.Address
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.address.ui.components.AddressForm
import com.evothings.mhand.presentation.feature.address.ui.components.list.AddressList
import com.evothings.mhand.presentation.feature.shared.checkbox.Checkbox
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun CourierDelivery(
    addresses: List<Address>,
    selected: Int,
    saveAddressState: Boolean,
    onSelect: (Int) -> Unit,
    onAddressListChanged: () -> Unit,
    onChangeNewAddress: (Address) -> Unit,
    onCheckSaveAddress: () -> Unit,
    onClickMap: (String) -> Unit
) {

    if (addresses.isNotEmpty()) {
        SelectAddress(
            addresses = addresses,
            selected = selected,
            onSelect = onSelect,
            onAddressChange = onAddressListChanged,
            openAddressMap = onClickMap,
        )
    } else {
        Column {
            AddressForm(
                existing = null,
                onChangeAddress = onChangeNewAddress,
                onClickMap = onClickMap
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.medium)
            )
            Checkbox(
                title = stringResource(R.string.save_address_checkbox),
                isChecked = saveAddressState,
                onCheck = onCheckSaveAddress
            )
        }
    }

}

@Composable
private fun SelectAddress(
    addresses: List<Address>,
    selected: Int,
    onSelect: (Int) -> Unit,
    onAddressChange: () -> Unit,
    openAddressMap: (String) -> Unit,
) {
    Text(
        text = stringResource(R.string.shipping_address),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(
        modifier = Modifier
            .height(MaterialTheme.spacers.medium)
    )
    AddressList(
        addresses = addresses,
        selected = selected,
        isProfile = false,
        onSelect = onSelect,
        onAddressChange = onAddressChange,
        openAddressMap = openAddressMap
    )
    Spacer(
        modifier = Modifier
            .height(MaterialTheme.spacers.extraLarge)
    )
}