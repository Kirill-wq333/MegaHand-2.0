package com.evothings.mhand.presentation.feature.coupon.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.auth.ui.components.PrivacyPolicyText
import com.evothings.domain.feature.coupon.model.CouponForm
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.chooseCity.ChooseCityModal
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.feature.shared.text.LabelTextField
import com.evothings.mhand.presentation.feature.shared.text.TrailingButtonTextField
import com.evothings.mhand.presentation.feature.shared.text.transform.TextMasks
import com.evothings.mhand.presentation.feature.shared.text.transform.rememberMaskVisualTransformation
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun GatherCouponForm(
    modifier: Modifier,
    bonusAmount: Int,
    onFill: (CouponForm) -> Unit,
    openPrivacyPolicy: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    var selectCityBottomSheetVisible by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    val isFilled by remember {
        derivedStateOf {
            name.isNotEmpty()
                    && surname.isNotEmpty()
                    && (phone.isNotEmpty() && phone.length == 11)
                    && city.isNotEmpty()
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.gather_coupon_title, bonusAmount),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.medium)
        )
        Text(
            text = stringResource(R.string.coupon_form_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary.copy(0.4f)
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Form(
            name = name,
            surname = surname,
            phone = phone,
            city = city,
            onChangeName = { name = it },
            onChangeSurname = { surname = it },
            onChangePhone = { phone = it },
            onChangeCityValue = { city = it },
            onClickSelectCity = {
                selectCityBottomSheetVisible = true
                focusManager.clearFocus()
            }
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.coupon_form_button),
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = ColorTokens.Graphite,
            isEnabled = isFilled,
            onClick = {
                onFill(
                    CouponForm(
                        name = name,
                        surname = surname,
                        city = city,
                        phone = phone
                    )
                )
            }
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.normal)
        )
        PrivacyPolicyText(
            buttonLabel = stringResource(id = R.string.coupon_form_button),
            onClick = openPrivacyPolicy
        )
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
private fun Form(
    name: String,
    surname: String,
    phone: String,
    city: String,
    onChangeName: (String) -> Unit,
    onChangeSurname: (String) -> Unit,
    onChangePhone: (String) -> Unit,
    onChangeCityValue: (String) -> Unit,
    onClickSelectCity: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LabelTextField(
            modifier = Modifier.weight(0.5f),
            value = name,
            label = stringResource(id = R.string.name),
            onValueChange = onChangeName
        )
        Spacer(
            modifier = Modifier
                .width(MaterialTheme.spacers.medium)
        )
        LabelTextField(
            modifier = Modifier.weight(0.5f),
            value = surname,
            label = stringResource(id = R.string.surname),
            onValueChange = onChangeSurname
        )
    }

    Spacer(
        modifier = Modifier
            .height(MaterialTheme.spacers.medium)
    )

    LabelTextField(
        value = phone,
        label = stringResource(id = R.string.phone_number),
        placeholder = "+7 (___) ___ __-__",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        maxLength = 11,
        visualTransformation = rememberMaskVisualTransformation(mask = TextMasks.phone),
        onValueChange = onChangePhone
    )

    Spacer(
        modifier = Modifier
            .height(MaterialTheme.spacers.medium)
    )

    TrailingButtonTextField(
        value = city,
        label = stringResource(id = R.string.city),
        buttonLabel = stringResource(id = R.string.choose),
        onValueChange = onChangeCityValue,
        onClickTrailingButton = onClickSelectCity
    )

}