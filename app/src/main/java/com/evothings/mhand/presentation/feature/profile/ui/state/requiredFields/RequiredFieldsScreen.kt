    package com.evothings.mhand.presentation.feature.profile.ui.state.requiredFields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.text.DatePickerTextField
import com.evothings.mhand.presentation.feature.shared.text.LabelTextField
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.feature.shared.text.TrailingButtonTextField
import com.evothings.mhand.presentation.feature.shared.text.transform.TextMasks
import com.evothings.mhand.presentation.feature.shared.text.transform.rememberMaskVisualTransformation
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Preview
@Composable
private fun RequiredFieldsScreenPreview() {
    MegahandTheme {
        RequiredFieldsScreen()
    }
}

@Composable
fun RequiredFieldsScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.onSecondary)
    ) {
        Content(
            onChangePhone = {},
            selected = false
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    onChangePhone: (String) -> Unit,
    selected: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.paddings.extraGiant)
    ) {
        Text(
            text = stringResource(R.string.fill_profile_title),
            color = colorScheme.secondary,
            style = MegahandTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Text(
            text = stringResource(R.string.fill_profile_subtitle),
            color = colorScheme.secondary.copy(.4f),
            style = MegahandTypography.bodyLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Data(
            onChangePhone = onChangePhone
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.proceed),
            textColor = if (selected) colorScheme.secondary else colorScheme.onSecondary,
            backgroundColor = if (selected) colorScheme.primary else colorScheme.secondary.copy(.1f),
            onClick = {}
        )
    }

}

@Composable
private fun Data(
    modifier: Modifier = Modifier,
    onChangePhone: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    var selectCityBottomSheetVisible by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
        ) {
            TextAndTextField(
                modifier = Modifier
                    .weight(.5f),
                text = stringResource(R.string.name),
                visibleAttention = true,
                visiblePrize = true,
                onValueChange = { },
                textField = name
            )
            TextAndTextField(
                modifier = Modifier
                    .weight(.5f),
                text = stringResource(R.string.surname),
                visibleAttention = true,
                visiblePrize = true,
                textField = surname,
                onValueChange = { surname = it }
            )
        }
        LabelTextField(
            value = phone,
            label = stringResource(id = R.string.phone_number),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            visualTransformation = rememberMaskVisualTransformation(mask = TextMasks.phone),
            maxLength = 11,
            onValueChange = onChangePhone
        )
        TextAndTextField(
            text = stringResource(R.string.profile_email),
            visiblePrize = true,
            onValueChange = {  },
            textField = email
        )
        TrailingButtonTextField(
            value = city,
            label = stringResource(R.string.city),
            buttonLabel = stringResource(R.string.choose),
            onValueChange = {city = it},
            onClickTrailingButton = {
                selectCityBottomSheetVisible = true
                focusManager.clearFocus()
            },
            visiblePrize = true
        )
        Column {
            Text(
                text = stringResource(R.string.date_of_birth),
                color = colorScheme.secondary.copy(.6f),
                style = MegahandTypography.bodyLarge,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.paddings.medium)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
            DatePickerTextField(
                date = date,
                onDateChange = {}
            )
        }
    }
}



@Composable
fun TextAndTextField(
    modifier: Modifier = Modifier,
    text: String,
    visiblePrize: Boolean = false,
    visibleAttention: Boolean = false,
    colorPrize: Color = colorScheme.secondary.copy(.6f),
    textField: String,
    onValueChange: (String) -> Unit
) {
    var textField by remember { mutableStateOf(textField) }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.paddings.medium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = colorScheme.secondary.copy(.6f),
                style = MegahandTypography.bodyLarge
            )
            if (visiblePrize) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_prize),
                    contentDescription = null,
                    tint = colorPrize,
                    modifier = Modifier
                        .size(16.dp)
                )
            }
            if (visibleAttention) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_attention),
                    contentDescription = null,
                    tint = colorScheme.error,
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        MTextField(
            value = textField,
            onValueChange = onValueChange
        )
    }
}