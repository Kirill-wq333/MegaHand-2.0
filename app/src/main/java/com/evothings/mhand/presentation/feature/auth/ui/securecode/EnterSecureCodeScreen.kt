package com.evothings.mhand.presentation.feature.auth.ui.securecode

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.auth.ui.components.SecureCodeDashes
import com.evothings.mhand.presentation.feature.auth.ui.components.keyboard.DigitalKeyboard
import com.evothings.mhand.presentation.feature.auth.viewmodel.securecode.SecureCodeContract
import com.evothings.mhand.presentation.feature.auth.viewmodel.securecode.SecureCodeViewModel
import com.evothings.mhand.presentation.feature.shared.TurnBackHeader
import com.evothings.mhand.presentation.feature.shared.text.util.convertSecsToClock
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.utils.sdkutil.Vibrator
import com.evothings.mhand.presentation.utils.widget.sendUpdateWidgetBroadcast

@Preview
@Composable
private fun EnterSecureCodeContentPreview() {
    MegahandTheme {
        EnterSecureCodeContent(
            codeErrorState = false,
            isLocked = false,
            timerRemainingSeconds = 0,
            checkSecureCode = {},
            onClickRemind = {},
            disableErrorState = {},
            onBack = {}
        )
    }
}

@Composable
fun EnterSecureCodeScreen(
    vm: SecureCodeViewModel,
    phone: String,
    openProfile: () -> Unit,
    openResetCode: (phone: String) -> Unit,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("SecureCodePrefs", Context.MODE_PRIVATE) }

    val codeErrorState by vm.errorState.collectAsState()

    val isLocked by vm.isLocked.collectAsState()
    val lockTimer by vm.lockTimer.collectAsState()

    LaunchedEffect(Unit) {
        val savedTimer = sharedPreferences.getInt("lockTimer", 0)
        if (savedTimer > 0) {
            vm.handleEvent(SecureCodeContract.Event.SetLockTimer(savedTimer))
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            sharedPreferences.edit().putInt("lockTimer", lockTimer).apply()
        }
    }

    LaunchedEffect(codeErrorState) {
        if (codeErrorState) {
            Vibrator.makeVibration(200L, context)
        }
    }

    LaunchedEffect(vm.effect) {
        vm.effect.collect {
            when(it) {
                is SecureCodeContract.Effect.UpdateWidgetData -> sendUpdateWidgetBroadcast(context)
                is SecureCodeContract.Effect.NavigateToProfile -> openProfile()
                is SecureCodeContract.Effect.NavigateToConfirmCode -> openResetCode(phone)
                is SecureCodeContract.Effect.ShowErrorToast ->
                    vm.handleEvent(SecureCodeContract.Event.SetLockTimer(lockTimer))
            }
        }
    }

    EnterSecureCodeContent(
        codeErrorState = codeErrorState,
        isLocked = isLocked,
        timerRemainingSeconds = lockTimer,
        checkSecureCode = { code ->
            vm.handleEvent(
                SecureCodeContract.Event.CheckSecureCode(code, phone)
            )
        },
        onClickRemind = {
            vm.handleEvent(SecureCodeContract.Event.SendConfirmCodeToReset(phone))
            openResetCode(phone)
        },
        disableErrorState = {
            vm.handleEvent(SecureCodeContract.Event.DisableConfirmErrorState)
        },
        onBack = onBack
    )

}

@Composable
private fun EnterSecureCodeContent(
    codeErrorState: Boolean,
    isLocked: Boolean,
    timerRemainingSeconds: Int,
    checkSecureCode: (String) -> Unit,
    onClickRemind: () -> Unit,
    disableErrorState: () -> Unit,
    onBack: () -> Unit
) {

    var code by rememberSaveable { mutableStateOf("") }
    var titleFontSize by remember { mutableStateOf(20.sp) }

    Scaffold(
        topBar = {
            TurnBackHeader(
                label = stringResource(R.string.auth_code),
                showArrow = false,
            )
        },
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SecureCodeDashes(
                    codeLength = code.length,
                    errorState = codeErrorState,
                    isActive = !isLocked
                )
                Spacer(
                    modifier = Modifier.height(96.dp)
                )
                DigitalKeyboard(
                    isEnabled = !isLocked,
                    onClickDigit = { newDigit ->
                        if (code.length < 4) {
                            code += newDigit
                        }

                        if (code.length == 4) {
                            checkSecureCode(code)
                        }
                    },
                    onClickDelete = {
                        code = code.dropLast(1)
                        disableErrorState()
                    },
                    onClickExit = onBack
                )
            }
        },
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPadding)
                .padding(
                    start = 36.dp,
                    end = 36.dp,
                    top = 48.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                text = stringResource(R.string.enter_secure_code),
                textAlign = TextAlign.Center,
                fontSize = titleFontSize,
                maxLines = 1,
                style = typography.headlineMedium,
                onTextLayout = {
                    if (it.hasVisualOverflow) {
                        titleFontSize *= .85f
                    }
                }
            )
            if (!isLocked) {
                Text(
                    text = stringResource(R.string.forgot_secure_code),
                    style = typography.bodyLarge,
                    color = colorScheme.secondary.copy(alpha = 0.4f),
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onClickRemind
                        )
                )
            } else {
                Text(
                    text = stringResource(
                        id = R.string.secure_code_input_blocked,
                        timerRemainingSeconds.convertSecsToClock()
                    ),
                    style = typography.bodyLarge,
                    color = colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}