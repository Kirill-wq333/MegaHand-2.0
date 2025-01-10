package com.evothings.mhand.presentation.feature.cart.ui.components.makingAnOrderComponents

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun PersonalData(
    modifier: Modifier = Modifier
) {

    var name by remember { mutableStateOf("Имя") }
    var surname by remember { mutableStateOf("Фамилия") }
    var address by remember { mutableStateOf("E-mail") }
    var phoneNumber by remember { mutableStateOf("Телефон") }

    Column(
        modifier = modifier
            .padding(
                vertical = MaterialTheme.paddings.giant,
                horizontal = MaterialTheme.paddings.extraGiant
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(R.string.personal_data),
            color = colorScheme.secondary,
            style = MegahandTypography.titleLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        TextAndTextField(
            text = name
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        TextAndTextField(
            text = surname
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        TextAndTextField(
            text = phoneNumber
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        TextAndTextField(
            text = address
        )
    }

}

@Composable
private fun TextAndTextField(
    modifier: Modifier = Modifier,
    text: String
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = text,
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.paddings.medium)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        MTextField(
            value = "",
            onValueChange = {},
        )
    }

}