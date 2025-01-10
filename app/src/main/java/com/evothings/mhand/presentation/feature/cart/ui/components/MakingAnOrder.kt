package com.evothings.mhand.presentation.feature.cart.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.feature.cart.ui.components.makingAnOrderComponents.PersonalData
import com.evothings.mhand.presentation.feature.cart.ui.components.makingAnOrderComponents.ReceiptMethodAndAddress
import com.evothings.mhand.presentation.feature.cart.ui.components.makingAnOrderComponents.SystemLoyality
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.spacers


@Preview
@Composable
private fun MakingAndOrderScreenPreview() {
    MegahandTheme(false) {
        MakingAnOrderScreen()
    }
}

@Composable
fun MakingAnOrderScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorScheme.onSecondary)
    ) {
        Content()
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        PersonalData()

        Spacer(modifier = Modifier.height(30.dp))

        ReceiptMethodAndAddress()
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorScheme.secondary)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        SystemLoyality()
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorScheme.secondary)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))

    }

}


