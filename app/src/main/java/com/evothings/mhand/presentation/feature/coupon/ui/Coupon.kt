package com.evothings.mhand.presentation.feature.coupon.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.evothings.domain.feature.coupon.model.CouponForm
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.auth.ui.components.PrivacyPolicyText
import com.evothings.mhand.presentation.feature.coupon.viewmodel.CouponContract
import com.evothings.mhand.presentation.feature.coupon.viewmodel.CouponViewModel
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.feature.shared.text.TrailingButtonTextField
import com.evothings.mhand.presentation.feature.shared.text.transform.TextMasks
import com.evothings.mhand.presentation.feature.shared.text.transform.rememberMaskVisualTransformation
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes
import com.evothings.mhand.presentation.utils.sdkutil.openPrivacyPolicyPage

@Composable
fun Coupon(
    vm: CouponViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    openConfirmationScreen: (String) -> Unit
){
    val context = LocalContext.current

    val couponAmount by vm.bonusAmount.collectAsState()

    LaunchedEffect(vm.effect) {
        vm.effect.collect {
            when(it) {
                is CouponContract.Effect.OpenConfirmationScreen -> openConfirmationScreen(it.phone)
                is CouponContract.Effect.ShowToast ->
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    MhandModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp
                    )
                )
                .background(color = colorScheme.onSecondary)
        ) {
            Content(
                bonusAmount = couponAmount,
                onFill = { vm.handleEvent(CouponContract.Event.SendForm(it)) },
                openPrivacyPolicy = { openPrivacyPolicyPage(context) }
            )
        }
    }
}

@Composable
private fun Content(
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
        modifier = Modifier
            .padding(MaterialTheme.paddings.extraGiant),
        horizontalAlignment = Alignment.Start
    ) {
        TextItem(
            text = stringResource(R.string.gather_coupon_title, bonusAmount),
            color = colorScheme.secondary,
            fontSize = 20.sp,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
            fontWeight = FontWeight.W500
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        TextItem(
            text = stringResource(R.string.coupon_form_subtitle),
            color = colorScheme.secondary.copy(0.4f),
            fontSize = 16.sp,
            fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
            fontWeight = FontWeight.W400
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        NameAndSurnameTextField(
            name = name,
            surname = surname,
            onChangeName = {name = it},
            onChangeSurname = {surname = it}
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        NumberPhoneTextField(
            phone = phone,
            onChangePhone = { phone = it}
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        TrailingButtonTextField(
            value = city,
            label = stringResource(R.string.city),
            buttonLabel = stringResource(R.string.choose),
            onValueChange = {city = it},
            onClickTrailingButton = {
                selectCityBottomSheetVisible = true
                focusManager.clearFocus()
            }
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
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
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        PrivacyPolicyText(
            buttonLabel = stringResource(id = R.string.coupon_form_button),
            onClick = openPrivacyPolicy
        )
    }
}

@Composable
fun NameAndSurnameTextField(
    name: String,
    surname: String,
    onChangeName: (String) -> Unit,
    onChangeSurname: (String) -> Unit,
){

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.name),
                color = colorScheme.secondary.copy(0.6f),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400)))
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
            MTextField(
                modifier = Modifier
                    .width(166.dp),
                value = name,
                errorState = false,
                errorText = "Like a leafs",
                placeholder = "",
                onValueChange = onChangeName
            )
        }
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.surname),
                color = colorScheme.secondary.copy(0.6f),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400)))
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
            MTextField(
                modifier = Modifier
                    .width(166.dp),
                value = surname,
                errorState = false,
                errorText = "Like a leafs",
                placeholder = "",
                onValueChange = onChangeSurname
            )
        }
    }
}

@Composable
fun NumberPhoneTextField(
    phone: String,
    onChangePhone: (String) -> Unit,
){

    Column {
        Text(
            text = stringResource(R.string.phone_number),
            color = colorScheme.secondary.copy(0.6f),
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily(listOf(Font(R.font.golos_400)))
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        MTextField(
            value = phone,
            errorState = false,
            errorText = "Like a leafs",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            placeholder = "",
            maxLines = 11,
            visualTransformation = rememberMaskVisualTransformation(mask = TextMasks.phone),
            onValueChange = onChangePhone
        )
    }
}


@Composable
private fun TextItem(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    fontFamily: FontFamily
){
    Text(
        text = text,
        textAlign = TextAlign.Start,
        color = color,
        fontSize = fontSize,
        fontFamily = fontFamily,
        fontWeight = fontWeight
    )
}

