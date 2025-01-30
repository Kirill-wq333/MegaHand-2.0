package com.evothings.mhand.presentation.feature.auth.ui.securecode

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.auth.ui.components.SecureCodeDashes
import com.evothings.mhand.presentation.feature.auth.ui.components.keyboard.DigitalKeyboard
import com.evothings.mhand.presentation.feature.auth.viewmodel.securecode.SecureCodeContract
import com.evothings.mhand.presentation.feature.auth.viewmodel.securecode.SecureCodeViewModel
import com.evothings.mhand.presentation.feature.shared.TurnBackHeader
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.utils.sdkutil.Vibrator

@Preview
@Composable
private fun CreateSecureCodePreview() {
    val context = LocalContext.current
    MegahandTheme {
        Content(
            context = context,
            setCode = {},
            onBack = {}
        )
    }
}

@Composable
fun CreateSecureCodeScreen(
    vm: SecureCodeViewModel,
    phone: String,
    openProfile: () -> Unit,
    onBack: () -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(vm.effect) {
        vm.effect.collect {
            when(it) {
                is SecureCodeContract.Effect.ShowErrorToast ->
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                is SecureCodeContract.Effect.NavigateToProfile -> openProfile()

                is SecureCodeContract.Effect.NavigateToConfirmCode -> { /* no-op */ }
                is SecureCodeContract.Effect.UpdateWidgetData -> { /* no-op */ }
            }
        }
    }

    Content(
        context = context,
        setCode = { code ->
            vm.handleEvent(SecureCodeContract.Event.SetSecureCode(code, phone))
        },
        onBack = onBack
    )

}

@Composable
private fun Content(
    context: Context,
    setCode: (String) -> Unit,
    onBack: () -> Unit
) {

    val confirmationState = remember { mutableStateOf(false) }
    val confirmationErrorState = remember { mutableStateOf(false) }

    var code by rememberSaveable { mutableStateOf("") }
    var confirmationCode by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TurnBackHeader(
                label = stringResource(R.string.create_secure_code_title),
                showArrow = false,
            )
        },
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SecureCodeDashes(
                    codeLength = code.length,
                    errorState = confirmationErrorState.value
                )
                Spacer(
                    modifier = Modifier.height(96.dp)
                )
                DigitalKeyboard(
                    onClickDigit = { newDigit ->
                        if (code.length < 4) {
                            code += newDigit
                        }

                        if (code.length != 4) {
                            return@DigitalKeyboard
                        }

                        if (!confirmationState.value) {
                            confirmationState.value = true
                            confirmationCode = code
                            code = ""
                            return@DigitalKeyboard
                        }

                        if (code == confirmationCode) {
                            setCode(code)
                        } else {
                            Vibrator.makeVibration(200L, context)
                            confirmationErrorState.value = true
                        }
                    },
                    onClickDelete = {
                        code = code.dropLast(1)
                        confirmationErrorState.value = false
                    },
                    onClickExit = onBack
                )
            }
        }
    ) { scaffoldPadding ->
        Text(
            text = stringResource(
                id = if (!confirmationState.value) {
                    R.string.secure_code_create
                } else {
                    R.string.enter_code_one_more_time
                }
            ),
            style = typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPadding)
                .padding(
                    vertical = 48.dp,
                    horizontal = 36.dp
                )
        )
    }

}