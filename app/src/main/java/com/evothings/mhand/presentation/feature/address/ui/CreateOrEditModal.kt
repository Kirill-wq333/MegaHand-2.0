package com.evothings.mhand.presentation.feature.address.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.evothings.domain.feature.address.model.Address
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.address.ui.components.AddressForm
import com.evothings.mhand.presentation.feature.address.viewmodel.AddressViewModel
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun CreateOrEditAddressModal(
    vm: AddressViewModel,
    modifier: Modifier,
    existingAddress: Address?,
    isEditMode: Boolean,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    openAddressMap: (String) -> Unit
) {

    var address by remember { mutableStateOf(existingAddress ?: Address()) }

    val saveButtonEnabled = remember(address) {
        with(address) {
            city.isNotEmpty() && street.isNotEmpty() && house.isNotEmpty() && flat.isNotEmpty()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AddressForm(
            existing = existingAddress,
            onChangeAddress = { address = it },
            onClickMap = openAddressMap
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        AddressActions(
            onCancel = onDismiss,
            saveButtonEnabled = saveButtonEnabled,
            onSave = {
                if (isEditMode) {
                    vm.editAddress(address)
                } else {
                    vm.createAddress(address)
                }
                onSave()
            }
        )
    }

}

@Composable
private fun AddressActions(
    onCancel: () -> Unit,
    saveButtonEnabled: Boolean,
    onSave: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.cancel),
            backgroundColor = Color.Transparent,
            borderColor = MaterialTheme.colorScheme.secondary.copy(0.1f),
            onClick = onCancel
        )
        Spacer(
            modifier = Modifier
                .width(MaterialTheme.spacers.medium)
        )
        Button(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.save),
            backgroundColor = ColorTokens.Sunflower,
            textColor = ColorTokens.Graphite,
            isEnabled = saveButtonEnabled,
            onClick = onSave
        )
    }
}