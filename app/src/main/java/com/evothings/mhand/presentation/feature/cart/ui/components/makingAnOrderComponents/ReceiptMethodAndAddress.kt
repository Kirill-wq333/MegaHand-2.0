package com.evothings.mhand.presentation.feature.cart.ui.components.makingAnOrderComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.icon.SmallIconButton
import com.evothings.mhand.presentation.feature.shared.checkbox.Checkbox
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun ReceiptMethodAndAddress(
    modifier: Modifier = Modifier
) {

    var address by remember { mutableStateOf("Тольятти, ул. Революционная 52") }

    Column(
        modifier = modifier
            .padding(horizontal = MaterialTheme.paddings.extraGiant),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        ReceiptMethod()
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Address(
            address = address,
            visibleAddress = true,
            addedNewAddress = {}
        )
    }
}

@Composable
fun ReceiptMethod(
    modifier: Modifier = Modifier
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
        Checkbox(
            title = stringResource(R.string.option_pickup_point),
            onCheck = {},
            isChecked = false
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Checkbox(
            title = stringResource(R.string.option_courier),
            onCheck = {},
            isChecked = false
        )
    }
}

@Composable
fun Address(
    modifier: Modifier = Modifier,
    address: String,
    visibleAddress: Boolean = false,
    addedNewAddress: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.shipping_address),
            color = colorScheme.secondary,
            style = MegahandTypography.headlineSmall
        )
        if (visibleAddress) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = colorScheme.secondary.copy(.1f),
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.paddings.extraLarge),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        title = address,
                        onCheck = {},
                        isChecked = false
                    )

                    SmallIconButton(
                        modifier = Modifier
                            .rotate(90f),
                        icon = ImageVector.vectorResource(id = R.drawable.ic_other),
                        onClick = {}
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorScheme.secondary.copy(.05f))
                .clickable { addedNewAddress() },
            contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.paddings.extraLarge),
                text = stringResource(R.string.add_new_button),
                color = colorScheme.secondary,
                style = MegahandTypography.labelLarge
            )
        }
    }

}