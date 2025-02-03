package com.evothings.mhand.presentation.feature.profile.ui.state.data.bottomsheet

import android.util.Patterns
import android.widget.Space
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.util.Mock
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.onboarding.model.CardAlignment
import com.evothings.mhand.presentation.feature.onboarding.ui.components.BottomSheetLikeIndicator
import com.evothings.mhand.presentation.feature.profile.ui.state.requiredFields.TextAndTextField
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.chooseCity.ChooseCityModal
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.feature.shared.text.DatePickerTextField
import com.evothings.mhand.presentation.feature.shared.text.LabelTextField
import com.evothings.mhand.presentation.feature.shared.text.TrailingButtonTextField
import com.evothings.mhand.presentation.feature.shared.text.transform.TextMasks
import com.evothings.mhand.presentation.feature.shared.text.transform.rememberMaskVisualTransformation
import com.evothings.mhand.presentation.feature.shared.text.util.cleanPhoneNumber
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.date.DateValidator

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun EditProfileModalPreview() {
    MegahandTheme {
        Surface {
            ModalBottomSheet(
                onDismissRequest = {}
            ) {
                RefactorProfile(
                    model = Profile(),
                    onCancel = { },
                    onSaveChanges = { _, _, _ -> }
                )
            }
        }
    }
}

@Composable
fun RefactorProfile(
    modifier: Modifier = Modifier,
    model: Profile,
    onCancel: () -> Unit,
    onSaveChanges: (model: Profile, changePhone: Boolean, phone: String) -> Unit
) {

    var firstName by remember { mutableStateOf(model.firstName) }
    var lastName by remember { mutableStateOf(model.lastName) }
    var phone by remember { mutableStateOf(model.phoneNumber.cleanPhoneNumber()) }
    var email by remember { mutableStateOf(model.email) }
    var birthday by remember { mutableStateOf(model.birthday) }
    var city by remember { mutableStateOf(model.city) }

    val isSaveButtonEnabled by remember {
        derivedStateOf {
            val emailValid = email.matches(Patterns.EMAIL_ADDRESS.toRegex())
            val phoneValid = phone.length == 11
            val isDateValid = DateValidator.isBeforeToday(birthday)

            (firstName.isNotEmpty() && lastName.isNotEmpty() && phoneValid) && // <- Required fields
                    (email.isEmpty() || emailValid) && (birthday.isEmpty() || isDateValid) // <- Not required
        }
    }

    var cityBottomSheetIsVisible by remember { mutableStateOf(false) }

    val phoneNumberIsChanged by remember {
        derivedStateOf {
            phone.cleanPhoneNumber() != model.phoneNumber.cleanPhoneNumber()
        }
    }

    val newProfile by rememberUpdatedState(
        newValue = Profile(
            firstName = firstName,
            lastName = lastName,
            birthday = birthday,
            city = city,
            email = email,
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .modalBottomSheetPadding()
    ) {
        Text(
            text = stringResource(R.string.profile_edit_label),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.mega))
        Column(
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
                    colorPrize = colorScheme.inverseSurface,
                    textField = firstName,
                    onValueChange = { firstName = it }
                )
                TextAndTextField(
                    modifier = Modifier
                        .weight(.5f),
                    text = stringResource(R.string.surname),
                    visibleAttention = true,
                    visiblePrize = true,
                    colorPrize = colorScheme.inverseSurface,
                    textField = lastName,
                    onValueChange = { lastName = it }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LabelTextField(
                    value = phone,
                    label = stringResource(id = R.string.phone_number),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    visualTransformation = rememberMaskVisualTransformation(mask = TextMasks.phone),
                    maxLength = 11,
                    onValueChange = { phone = it }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
                Text(
                    text = stringResource(R.string.phone_change_code_warning),
                    color = colorScheme.secondary.copy(.4f),
                    style = MegahandTypography.bodyMedium
                )
            }
            TextAndTextField(
                text = stringResource(R.string.profile_email),
                visibleAttention = false,
                visiblePrize = true,
                colorPrize = colorScheme.inverseSurface,
                textField = email,
                onValueChange = { email = it }
            )
            TrailingButtonTextField(
                value = city,
                label = stringResource(R.string.city),
                buttonLabel = stringResource(R.string.choose),
                onValueChange = { city = it },
                onClickTrailingButton = {
                    cityBottomSheetIsVisible = true
                },
                visiblePrize = true,
                colorPrize = colorScheme.inverseSurface
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
                    date = birthday,
                    onDateChange = { birthday = it}
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.mega))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier
                    .weight(.5f),
                text = stringResource(R.string.cancel),
                textColor = colorScheme.secondary,
                borderColor = colorScheme.secondary.copy(.1f),
                onClick = onCancel
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
            Button(
                modifier = Modifier
                    .weight(.5f),
                text = stringResource(R.string.save),
                isEnabled = isSaveButtonEnabled,
                textColor = colorScheme.secondary,
                backgroundColor = colorScheme.primary,
                onClick = { onSaveChanges(newProfile, phoneNumberIsChanged, phone) }
            )
        }
    }
    if (cityBottomSheetIsVisible) {
        MhandModalBottomSheet(
            onDismissRequest = { cityBottomSheetIsVisible = false }
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


