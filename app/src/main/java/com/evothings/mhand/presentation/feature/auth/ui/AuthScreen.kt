package com.evothings.mhand.presentation.feature.auth.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.auth.ui.components.PrivacyPolicyText
import com.evothings.mhand.presentation.feature.auth.viewmodel.AuthContract
import com.evothings.mhand.presentation.feature.auth.viewmodel.AuthViewModel
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.SmallIconButton
import com.evothings.mhand.presentation.feature.shared.checkbox.Checkbox
import com.evothings.mhand.presentation.feature.shared.hint.AnimatedHint
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.text.LabelTextField
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.feature.shared.text.transform.TextMasks
import com.evothings.mhand.presentation.feature.shared.text.transform.rememberMaskVisualTransformation
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.sdkutil.openPrivacyPolicyPage

private interface AuthCallback {
    fun openPrivacyPolicy()
    fun sendCode(phone: String, refCode: String = "")
    fun onBack()
}


@Composable
fun AuthScreen(
    vm: AuthViewModel,
    openMainScreen: () -> Unit,
    openConfirmationCode: (phone: String) -> Unit,
    openEnterSecureCode: (phone: String) -> Unit
) {
    val context = LocalContext.current

    val state by vm.state.collectAsStateWithLifecycle()

    BackHandler(
        enabled = true,
        onBack = openMainScreen
    )

    LaunchedEffect(vm.effect) {
        vm.effect.collect { effect ->
            when(effect) {
                is AuthContract.Effect.NavigateToCode -> openConfirmationCode(effect.phone)
                is AuthContract.Effect.NavigateToSecureCode -> openEnterSecureCode(effect.phone)
                is AuthContract.Effect.ShowErrorToast ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        vm.handleEvent(AuthContract.Event.ResetState)
    }

    val callback =
        object : AuthCallback {

            override fun onBack() = openMainScreen()

            override fun openPrivacyPolicy() {
                openPrivacyPolicyPage(context)
            }

            override fun sendCode(phone: String, refCode: String) =
                vm.handleEvent(AuthContract.Event.SendAuthData(phone, refCode))

        }
    Content(
        state = state,
        callback = callback
    )

}


@Composable
private fun Content(
    state: AuthContract.State,
    callback: AuthCallback
) {

    var phone by rememberSaveable { mutableStateOf("") }
    var refCode by rememberSaveable { mutableStateOf("") }

    var agreementIsChecked by rememberSaveable { mutableStateOf(false) }

    val isButtonEnabled by remember {
        derivedStateOf {
            agreementIsChecked = phone.length == 10
            phone.length == 10
        }
    }

    Scaffold(
        bottomBar = {
            if (state is AuthContract.State.Idle) {
                val focusManager = LocalFocusManager.current
                NextButtonAndPrivacyPolicyText(
                    onNext = {
                        focusManager.clearFocus()
                        callback.sendCode(phone, refCode)
                    },
                    isEnabled = isButtonEnabled,
                    onPrivacyPolicy = callback::openPrivacyPolicy,
                    agreementIsChecked = agreementIsChecked,
                    agreement = { agreementIsChecked = !agreementIsChecked }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when(state) {
                is AuthContract.State.Loading -> {
                    LoadingScreen()
                }
                is AuthContract.State.Idle -> {
                    InputFields(
                        phone = phone,
                        refCode = refCode,
                        onChangePhone = { phone = it },
                        onChangeRefCode = { refCode = it }
                    )
                }
            }
        }
    }

}

@Composable
private fun InputFields(
    phone: String,
    refCode: String,
    onChangePhone: (String) -> Unit,
    onChangeRefCode: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LabelTextField(
            value = phone,
            label = stringResource(id = R.string.phone_number),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            visualTransformation = rememberMaskVisualTransformation(mask = TextMasks.phone),
            errorText = "Заполните поле",
            maxLength = 10,
            onValueChange = onChangePhone
        )
        InviteCodeInput(
            value = refCode,
            onValueChange = onChangeRefCode
        )
    }
}




@Composable
private fun InviteCodeInput(
    value: String,
    onValueChange: (String) -> Unit
) {

    val isHintVisible = remember { mutableStateOf(false) }
    var titleFontSize by remember { mutableStateOf(16.sp) }

    Column(
        verticalArrangement = Arrangement.spacedBy(9.dp),
    ) {
        Text(
            text = stringResource(R.string.invite_link_field_title),
            style = typography.bodyLarge,
            fontSize = titleFontSize,
            maxLines = 1,
            color = colorScheme.secondary.copy(alpha = 0.6f),
            modifier = Modifier.padding(horizontal = 6.dp),
            onTextLayout = {
                if (it.hasVisualOverflow) {
                    titleFontSize *= .9f
                }
            }
        )
        MTextField(
            value = value,
            onValueChange = onValueChange,
            trailing = {
                SmallIconButton(
                    icon = ImageVector.vectorResource(id = R.drawable.ic_question),
                    iconPadding = 2.dp,
                    tint = colorScheme.secondary.copy(0.4f),
                    onClick = { isHintVisible.value = !isHintVisible.value }
                )
            }
        )
        AnimatedHint(
            modifier = Modifier
                .align(Alignment.End),
            text = stringResource(id = R.string.invite_code_hint_text),
            visible = isHintVisible.value,
            onHide = { isHintVisible.value = false }
        )
    }

}

@Composable
fun NextButtonAndPrivacyPolicyText(
    onNext: () -> Unit,
    isEnabled: Boolean,
    onPrivacyPolicy: () -> Unit,
    agreementIsChecked: Boolean,
    agreement: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.paddings.extraGiant)
    ) {
        Checkbox(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(R.string.agreement),
            onCheck = agreement,
            isChecked = agreementIsChecked
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        Button(
            text = stringResource(R.string.next),
            backgroundColor = colorScheme.primary,
            textColor = colorScheme.secondary,
            isEnabled = isEnabled,
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
        PrivacyPolicyText(
            buttonLabel = stringResource(R.string.login),
            onClick = onPrivacyPolicy
        )
    }
}

@Preview
@Composable
private fun PreviewAuth() {
    MegahandTheme(false) {
        Content(
            state = AuthContract.State.Idle,
            callback = object : AuthCallback {
                override fun onBack() {}
                override fun openPrivacyPolicy() {}
                override fun sendCode(phone: String, refCode: String) {}
            }
        )
    }
}
