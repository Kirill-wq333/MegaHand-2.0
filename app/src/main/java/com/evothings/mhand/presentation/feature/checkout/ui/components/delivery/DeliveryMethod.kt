package com.evothings.mhand.presentation.feature.checkout.ui.components.delivery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.checkout.model.DeliveryOption
import com.evothings.domain.feature.checkout.model.PickupPoint
import com.evothings.domain.util.Mock
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.radio.RadioButton
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun DeliveryMethod(
    deliveryOption: DeliveryOption,
    addresses: List<Address>,
    selected: Int,
    pickupPoints: List<PickupPoint>,
    pickupCity: String,
    selectedPickupPoint: PickupPoint,
    saveNewAddress: Boolean,
    onSelectAddress: (Int) -> Unit,
    onAddressListChanged: () -> Unit,
    onSelectDeliveryOption: (DeliveryOption) -> Unit,
    onChangePickupCity: (String) -> Unit,
    onSelectPickupPoint: (PickupPoint) -> Unit,
    onChangeNewAddress: (Address) -> Unit,
    onCheckSaveAddress: () -> Unit,
    openAddressMap: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacers.extraLarge
            )
    ) {
        Text(
            text = stringResource(R.string.delivery_method_heading),
            style = typography.headlineMedium,
            fontSize = 24.sp,
            color = colorScheme.secondary
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        DeliveryOptionPicker(
            currentOption = deliveryOption,
            onSelect = onSelectDeliveryOption
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
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
            PickUpPoint(
                points = pickupPoints,
                city = pickupCity,
                selectedPoint = selectedPickupPoint,
                onChangeCity = onChangePickupCity,
                onSelectPoint = onSelectPickupPoint,
            )
        }
    }
}

@Composable
private fun DeliveryOptionPicker(
    currentOption: DeliveryOption,
    onSelect: (DeliveryOption) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.delivery_option),
            style = typography.headlineSmall,
            color = colorScheme.secondary
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.medium)
        )
        RadioButton(
            title = stringResource(R.string.option_pickup_point),
            isSelected = (currentOption == DeliveryOption.PICK_UP),
            onSelect = { onSelect(DeliveryOption.PICK_UP) }
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.normal)
        )
        RadioButton(
            title = stringResource(R.string.option_courier),
            isSelected = (currentOption == DeliveryOption.COURIER),
            onSelect = { onSelect(DeliveryOption.COURIER) }
        )
    }
}

@Preview
@Composable
private fun DeliveryMethodPreview() {
    MegahandTheme(false) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            DeliveryMethod(
                deliveryOption = DeliveryOption.PICK_UP,
                addresses = Mock.demoAddresses,
                selected = 0,
                pickupPoints = Mock.demoPickupPoints,
                pickupCity = "Тольятти",
                selectedPickupPoint = Mock.demoPickupPoints.first(),
                saveNewAddress = false,
                onSelectAddress = {},
                onSelectDeliveryOption = {},
                onChangePickupCity = {},
                onSelectPickupPoint = {},
                onChangeNewAddress = {},
                openAddressMap = {},
                onCheckSaveAddress = {},
                onAddressListChanged = {}
            )
        }
    }
}