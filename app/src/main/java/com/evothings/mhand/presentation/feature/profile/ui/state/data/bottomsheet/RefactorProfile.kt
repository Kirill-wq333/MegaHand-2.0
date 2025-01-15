package com.evothings.mhand.presentation.feature.profile.ui.state.data.bottomsheet

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.onboarding.model.CardAlignment
import com.evothings.mhand.presentation.feature.onboarding.ui.components.BottomSheetLikeIndicator
import com.evothings.mhand.presentation.feature.profile.ui.state.requiredFields.TextAndTextField
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.text.DatePickerTextField
import com.evothings.mhand.presentation.feature.shared.text.LabelTextField
import com.evothings.mhand.presentation.feature.shared.text.TrailingButtonTextField
import com.evothings.mhand.presentation.feature.shared.text.transform.TextMasks
import com.evothings.mhand.presentation.feature.shared.text.transform.rememberMaskVisualTransformation
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Preview
@Composable
private fun RefactorProfilePreview() {
    MegahandTheme {
        RefactorProfile(
            alignment = CardAlignment.BOTTOM
        )
    }
}

@Composable
fun RefactorProfile(
    modifier: Modifier = Modifier,
    alignment: CardAlignment
) {
    val indicatorAlign = remember(alignment) {
        if (alignment == CardAlignment.BOTTOM)
            Alignment.TopCenter
        else
            Alignment.BottomCenter
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.onSecondary,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
    ) {
        BottomSheetLikeIndicator(
            modifier = Modifier.align(indicatorAlign)
        )
        Content(
            onChangePhone = {}
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    onChangePhone: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    var selectCityBottomSheetVisible by remember { mutableStateOf(false) }

    var phone by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.paddings.extraGiant)
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
                    colorPrize = colorScheme.inverseSurface
                )
                TextAndTextField(
                    modifier = Modifier
                        .weight(.5f),
                    text = stringResource(R.string.name),
                    visibleAttention = true,
                    visiblePrize = true,
                    colorPrize = colorScheme.inverseSurface
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
                    onValueChange = onChangePhone
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
                Text(
                    text = stringResource(R.string.phone_change_code_warning),
                    color = colorScheme.secondary.copy(.4f),
                    style = MegahandTypography.bodyMedium
                )
            }
            TextAndTextField(
                text = stringResource(R.string.name),
                visibleAttention = false,
                visiblePrize = true,
                colorPrize = colorScheme.inverseSurface
            )
            TrailingButtonTextField(
                value = city,
                label = stringResource(R.string.city),
                buttonLabel = stringResource(R.string.choose),
                onValueChange = { city = it },
                onClickTrailingButton = {
                    selectCityBottomSheetVisible = true
                    focusManager.clearFocus()
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
                    date = date,
                    onDateChange = {}
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
                onClick = {}
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
            Button(
                modifier = Modifier
                    .weight(.5f),
                text = stringResource(R.string.save),
                textColor = colorScheme.secondary,
                backgroundColor = colorScheme.primary,
                onClick = {}
            )
        }
    }

}


