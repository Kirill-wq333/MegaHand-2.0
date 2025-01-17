package com.evothings.mhand.presentation.feature.address.ui.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.evothings.domain.feature.address.model.Address
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.address.ui.CreateOrEditAddressModal
import com.evothings.mhand.presentation.feature.address.viewmodel.AddressViewModel
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.SmallIconButton
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.feature.shared.radio.RadioButton
import com.evothings.mhand.presentation.feature.shared.text.saver.BooleanSaver
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun AddressList(
    vm: AddressViewModel = hiltViewModel(),
    addresses: List<Address>,
    selected: Int,
    isProfile: Boolean,
    onSelect: (Int) -> Unit,
    onAddressChange: () -> Unit,
    openAddressMap: (String) -> Unit,
) {

    var addressToEdit by remember { mutableStateOf<Address?>(null) }
    var editAddressBottomSheetVisible by rememberSaveable(Unit, BooleanSaver) { mutableStateOf(false) }

    val isDarkTheme = (colorScheme.secondary != ColorTokens.Graphite)
    val buttonBackgroundColor =
        if (isProfile && !isDarkTheme) {
            Color.White
        } else {
            colorScheme.secondary.copy(0.05f)
        }

    Column {
        repeat(addresses.size) { i ->
            val item = remember { addresses[i] }

            AddressItem(
                address = item.fullAddress,
                isSelected = (selected == i),
                enableWhiteBackground = isProfile,
                enableRadioButton = !isProfile,
                onSelect = { onSelect(i) },
                onSetDefault = {
                    vm.setAddressPrimary(item.id)
                    onSelect(i)
                },
                onEdit = {
                    addressToEdit = item
                    editAddressBottomSheetVisible = true
                },
                onDelete = { vm.deleteAddress(item.id) }
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.small)
            )
        }
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.add_new_button),
            backgroundColor = buttonBackgroundColor,
            borderColor = colorScheme.secondary.copy(0.05f),
            textColor = colorScheme.secondary,
            onClick = { editAddressBottomSheetVisible = true }
        )
    }


    if (editAddressBottomSheetVisible) {
        MhandModalBottomSheet(
            onDismissRequest = {
                editAddressBottomSheetVisible = false
                addressToEdit = null
            }
        ) { hide ->
            CreateOrEditAddressModal(
                modifier = Modifier.modalBottomSheetPadding(),
                vm = vm,
                existingAddress = addressToEdit,
                isEditMode = (addressToEdit != null),
                onDismiss = hide,
                onSave = { onAddressChange(); hide() },
                openAddressMap = openAddressMap
            )
        }
    }

}

@Composable
private fun AddressItem(
    address: String,
    isSelected: Boolean,
    enableWhiteBackground: Boolean,
    enableRadioButton: Boolean,
    onSelect: () -> Unit,
    onSetDefault: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    val borderColor =
        if (isSelected) {
            colorScheme.primary
        } else {
            colorScheme.secondary.copy(0.1f)
        }

    val isDarkTheme = (colorScheme.secondary != ColorTokens.Graphite)
    val background =
        if (enableWhiteBackground && !isDarkTheme) Color.White else Color.Transparent

    var addressSettingBottomSheetExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = MaterialTheme.shapes.medium
            )
            .background(
                color = background,
                shape = MaterialTheme.shapes.medium
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onSelect
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = MaterialTheme.spacers.medium
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                if (enableRadioButton) {
                    RadioButton(
                        title = address,
                        isSelected = isSelected,
                        onSelect = onSelect
                    )
                } else {
                    Text(
                        text = address,
                        style = typography.bodyLarge
                    )
                }
            }
            SmallIconButton(
                icon = ImageVector.vectorResource(id = R.drawable.ic_more),
                iconPadding = 0.dp,
                tint = colorScheme.secondary.copy(0.6f),
                backgroundColor = Color.Transparent,
                borderColor = Color.Transparent,
                onClick = { addressSettingBottomSheetExpanded = true },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (addressSettingBottomSheetExpanded) {
        MhandModalBottomSheet(
            onDismissRequest = { addressSettingBottomSheetExpanded = false }
        ) { hide ->
            EditAddressModal(
                modifier = Modifier.modalBottomSheetPadding(),
                onEdit = { onEdit(); hide() },
                onSetDefault = { onSetDefault(); hide() },
                onDelete = { onDelete(); hide() }
            )
        }
    }

}