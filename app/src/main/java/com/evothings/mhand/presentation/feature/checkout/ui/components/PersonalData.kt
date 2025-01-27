package com.evothings.mhand.presentation.feature.checkout.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.text.LabelTextField
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.feature.shared.text.transform.TextMasks
import com.evothings.mhand.presentation.feature.shared.text.transform.rememberMaskVisualTransformation
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun PersonalData(
    modifier: Modifier = Modifier,
    name: String,
    surname: String,
    phoneNumber: String,
    email: String,
    onChangeName: (String) -> Unit,
    onChangeSurname: (String) -> Unit,
    onChangePhone: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
) {

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
        LabelTextField(
            value = name,
            label = stringResource(id = R.string.name),
            onValueChange = onChangeName
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        LabelTextField(
            value = surname,
            label = stringResource(id = R.string.surname),
            onValueChange = onChangeSurname
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        LabelTextField(
            value = phoneNumber,
            visualTransformation = rememberMaskVisualTransformation(mask = TextMasks.phone),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            label = stringResource(id = R.string.phone_number),
            onValueChange = onChangePhone
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        LabelTextField(
            value = email,
            label = stringResource(id = R.string.profile_email),
            onValueChange = onChangeEmail
        )
    }

}

