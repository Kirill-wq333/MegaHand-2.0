package com.evothings.mhand.presentation.feature.address.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import com.evothings.domain.feature.address.model.Address
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.address.viewmodel.registry.AddressNavigationRegistry
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.chooseCity.ChooseCityModal
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.feature.shared.text.LabelTextField
import com.evothings.mhand.presentation.feature.shared.text.TrailingButtonTextField
import com.evothings.mhand.presentation.feature.shared.text.saver.StringSaver
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun AddressForm(
    existing: Address?,
    onChangeAddress: (Address) -> Unit,
    onClickMap: (String) -> Unit,
) {

    var cityBottomSheetVisible by remember { mutableStateOf(false) }

    val id = remember { mutableIntStateOf(existing?.id ?: 0) }
    var city by rememberSaveable(Unit, StringSaver) { mutableStateOf(existing?.city.orEmpty()) }
    var street by rememberSaveable(Unit, StringSaver) { mutableStateOf(existing?.street.orEmpty()) }
    var house by rememberSaveable(Unit, StringSaver) { mutableStateOf(existing?.house.orEmpty()) }
    var flat by rememberSaveable(Unit, StringSaver) { mutableStateOf(existing?.flat.orEmpty()) }

    LaunchedEffect(Unit) {
        val registryValue = AddressNavigationRegistry.get()

        if (registryValue != null) {
            id.intValue = registryValue.id
            city = registryValue.city
            street = registryValue.street
            house = registryValue.house
            flat = registryValue.flat
            onChangeAddress(registryValue)
            AddressNavigationRegistry.clear()
        }
    }

    LaunchedEffect(city, street, house, flat) {
        onChangeAddress(
            Address(
                id = id.intValue,
                city = city,
                house = house,
                flat = flat,
                street = street,
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {

        TrailingButtonTextField(
            value = city,
            label = stringResource(id = R.string.city),
            buttonLabel = stringResource(id = R.string.choose),
            onValueChange = { city = it },
            onClickTrailingButton = { cityBottomSheetVisible = true }
        )

        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.medium)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            LabelTextField(
                modifier = Modifier
                    .weight(1f, false),
                value = street,
                label = stringResource(R.string.street_placeholder),
                onValueChange = { street = it }
            )
            Spacer(
                modifier = Modifier
                    .width(MaterialTheme.spacers.medium)
            )
            IconButton(
                icon = ImageVector.vectorResource(id = R.drawable.ic_map),
                tint = MaterialTheme.colorScheme.secondary,
                borderColor = MaterialTheme.colorScheme.secondary.copy(0.1f),
                onClick = {
                    val addressInstance = Address(
                        id = id.intValue,
                        city = city,
                        house = house,
                        flat = flat,
                        street = street,
                    )
                    AddressNavigationRegistry.set(addressInstance)
                    onClickMap(city)
                },
                modifier = Modifier.weight(0.15f)
            )
        }

        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.medium)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            LabelTextField(
                value = house,
                label = stringResource(R.string.house_placeholder),
                modifier = Modifier.weight(0.5f, fill = false),
                onValueChange = { house = it },
            )
            Spacer(
                modifier = Modifier
                    .width(MaterialTheme.spacers.medium)
            )
            LabelTextField(
                value = flat,
                label = stringResource(id = R.string.flat_placeholder),
                onValueChange = { flat = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                modifier = Modifier.weight(0.5f, fill = false)
            )
        }

        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.medium)
        )

    }

    if (cityBottomSheetVisible) {
        MhandModalBottomSheet(
            onDismissRequest = { cityBottomSheetVisible = false }
        ) { hide ->
            ChooseCityModal(
                modifier = Modifier.modalBottomSheetPadding(),
                onDismiss = hide,
                onChoose = { city = it; hide() }
            )
        }
    }

}