package com.evothings.mhand.presentation.feature.checkout.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.checkout.model.DeliveryOption
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.checkout.ui.components.delivery.CDEKViewBox
import com.evothings.mhand.presentation.feature.checkout.ui.components.delivery.CourierDelivery
import com.evothings.mhand.presentation.feature.shared.radio.RadioButton
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun ReceiptMethodAndAddress(
    modifier: Modifier = Modifier,
    deliveryOption: DeliveryOption,
    addresses: List<Address>,
    selected: Int,
    selectedAddress: String,
    saveNewAddress: Boolean,
    onSelectAddress: (Int) -> Unit,
    onAddressListChanged: () -> Unit,
    onSelectDeliveryOption: (DeliveryOption) -> Unit,
    onChangeNewAddress: (Address) -> Unit,
    onCheckSaveAddress: () -> Unit,
    openAddressMap: (String) -> Unit,
    onClick: () -> Unit
) {


    Column(
        modifier = modifier
            .padding(horizontal = MaterialTheme.paddings.extraGiant),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        ReceiptMethod(
            currentOption = deliveryOption,
            onSelect = onSelectDeliveryOption
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        if (deliveryOption == DeliveryOption.COURIER) {
            CourierDelivery(
                addresses = addresses,
                selected = selected,
                onSelect = onSelectAddress,
                saveAddressState = saveNewAddress,
                onChangeNewAddress = onChangeNewAddress,
                onClickMap = openAddressMap,
                onAddressListChanged = onAddressListChanged,
                onCheckSaveAddress = onCheckSaveAddress
            )
        } else {
            Text(
                text = selectedAddress,
                style = typography.headlineMedium,
                color = colorScheme.secondary
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.extraLarge)
            )
            CDEKViewBox(
                onClick = onClick
            )
        }
    }
}

@Composable
fun ReceiptMethod(
    currentOption: DeliveryOption,
    onSelect: (DeliveryOption) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.receipt_method),
            color = colorScheme.secondary,
            style = MegahandTypography.headlineSmall
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        RadioButton(
            title = stringResource(R.string.option_pickup_point),
            onSelect = { onSelect(DeliveryOption.PICK_UP) },
            isSelected = (currentOption == DeliveryOption.PICK_UP)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        RadioButton(
            title = stringResource(R.string.option_courier),
            onSelect = { onSelect(DeliveryOption.COURIER) },
            isSelected = (currentOption == DeliveryOption.COURIER)
        )
    }
}
