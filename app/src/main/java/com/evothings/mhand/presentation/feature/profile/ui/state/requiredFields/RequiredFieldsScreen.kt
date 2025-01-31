package com.evothings.mhand.presentation.feature.profile.ui.state.requiredFields

import android.util.Patterns
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.chooseCity.ChooseCityModal
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.feature.shared.text.DatePickerTextField
import com.evothings.mhand.presentation.feature.shared.text.LabelTextField
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.feature.shared.text.TrailingButtonTextField
import com.evothings.mhand.presentation.feature.shared.text.transform.TextMasks
import com.evothings.mhand.presentation.feature.shared.text.transform.rememberMaskVisualTransformation
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.date.DateValidator


@Composable
fun RequiredFieldsScreen(
    modifier: Modifier = Modifier,
    model: Profile,
    onSave: (Profile) -> Unit
) {
    var name by rememberSaveable { mutableStateOf(model.firstName) }
    var surname by rememberSaveable { mutableStateOf(model.lastName) }
    var email by rememberSaveable { mutableStateOf(model.email) }
    var city by remember { mutableStateOf(model.city) }
    var date by remember { mutableStateOf(model.birthday) }
    var phone by remember { mutableStateOf(model.phoneNumber) }

    val isSaveButtonEnabled by remember {
        derivedStateOf {
            val emailValid = email.matches(Patterns.EMAIL_ADDRESS.toRegex())
            val dateValid = DateValidator.isBeforeToday(date)

            (name.isNotEmpty() && surname.isNotEmpty()) &&
                    (email.isEmpty() || emailValid) && (date.isEmpty() || dateValid)
        }
    }

    val newProfile by rememberUpdatedState(
        newValue = Profile(
            firstName = name,
            lastName = surname,
            birthday = date,
            city = city,
            email = email,
        )
    )

    var selectCityBottomSheetVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
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
                phone = phone,
                email = email,
                surname = surname,
                name = name,
                city = city,
                date = date,
                onChangeDate = { date = it },
                onChangeEmail = { email = it },
                onChangeName = { name = it },
                onChangeCity = { city = it },
                onChangeSurname = { surname = it },
                onClickCity = { selectCityBottomSheetVisible = true }

            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.proceed),
                backgroundColor = colorScheme.primary,
                textColor = ColorTokens.Graphite,
                onClick = { onSave(newProfile) },
                isEnabled = isSaveButtonEnabled
            )
        }
    }
    if (selectCityBottomSheetVisible) {
        MhandModalBottomSheet(
            onDismissRequest = { selectCityBottomSheetVisible = false }
        ) { hide ->
            ChooseCityModal(
                modifier = Modifier.modalBottomSheetPadding(),
                onDismiss = hide,
                onChoose = {
                    city = it; hide()
                }
            )
        }
    }
}

@Composable
private fun Data(
    email: String,
    surname: String,
    name: String,
    phone: String,
    city: String,
    date: String,
    onClickCity: () -> Unit,
    onChangeDate: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangeName: (String) -> Unit,
    onChangeCity: (String) -> Unit,
    onChangeSurname: (String) -> Unit
) {

    DataContent(
        name = name,
        surname = surname,
        email = email,
        city = city,
        date = date,
        phone = phone,
        enablePhoneField = false,
        onChangeName = onChangeName,
        onChangeSurname = onChangeSurname,
        onChangeEmail = onChangeEmail,
        onChangeDate = onChangeDate,
        onChangeCity = onChangeCity,
        onClickCity = onClickCity
    )

}


@Composable
private fun DataContent(
    name: String,
    surname: String,
    phone: String = "",
    email: String,
    city: String,
    date: String,
    enablePhoneField: Boolean = true,
    onChangeName: (String) -> Unit,
    onChangeSurname: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePhone: (String) -> Unit = {},
    onChangeDate: (String) -> Unit,
    onChangeCity: (String) -> Unit,
    onClickCity: () -> Unit
) {
    val focusManager = LocalFocusManager.current


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
                visiblePrize = name.isNotEmpty(),
                onValueChange = onChangeName,
                textField = name
            )
            TextAndTextField(
                modifier = Modifier
                    .weight(.5f),
                text = stringResource(R.string.surname),
                visibleAttention = true,
                visiblePrize = surname.isNotEmpty(),
                textField = surname,
                onValueChange = onChangeSurname
            )
        }
        if (enablePhoneField){
            LabelTextField(
                value = phone,
                label = stringResource(id = R.string.phone_number),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                visualTransformation = rememberMaskVisualTransformation(mask = TextMasks.phone),
                maxLength = 11,
                onValueChange = onChangePhone
            )
        }
        TextAndTextField(
            text = stringResource(R.string.profile_email),
            visiblePrize = email.isNotEmpty(),
            onValueChange = onChangeEmail,
            textField = email
        )
        TrailingButtonTextField(
            value = city,
            label = stringResource(R.string.city),
            buttonLabel = stringResource(R.string.choose),
            onValueChange = onChangeCity,
            onClickTrailingButton = {
                onClickCity()
                focusManager.clearFocus()
            },
            visiblePrize = city.isNotEmpty()
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
                onDateChange = onChangeDate
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