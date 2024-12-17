package com.evothings.mhand.presentation.feature.shared.screen.confirmCode.ui

//import android.content.Intent
//import android.widget.Toast
//import androidx.activity.compose.BackHandler
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.Stable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.evothings.mhand.R
//import com.evothings.mhand.presentation.feature.auth.ui.components.AuthCodeInput
//import com.evothings.mhand.presentation.feature.auth.ui.components.keyboard.DigitalKeyboard
//import com.evothings.mhand.presentation.feature.shared.TurnBackHeader
//import com.evothings.mhand.presentation.feature.shared.screen.confirmCode.viewmodel.ConfirmCodeContract
//import com.evothings.mhand.presentation.feature.shared.screen.confirmCode.viewmodel.ConfirmCodeViewModel
//import com.evothings.mhand.presentation.feature.captcha.HCaptchaActivity
//import com.evothings.mhand.presentation.feature.captcha.rememberCaptchaActivityLauncher
//import com.evothings.mhand.presentation.feature.shared.screen.confirmCode.viewmodel.model.ConfirmCodeUseCase
//import com.evothings.mhand.presentation.feature.shared.text.util.convertSecsToClock
//import com.evothings.mhand.presentation.theme.MegahandTheme
//import kotlinx.coroutines.delay
//
//internal interface AuthCodeCallback {
//    fun onBack() {}
//    fun resendCode() {}
//    fun confirmCode(code: String) {}
//    fun disableErrorState() {}
//    fun launchCaptchaConfirmation() {}
//}
//
//@Stable
//internal object EmptyAuthCodeCallback : AuthCodeCallback
//
//@Composable
//fun ConfirmCodeScreen(
//    vm: ConfirmCodeViewModel,
//    phone: String,
//    useCase: ConfirmCodeUseCase,
//    openCreateSecureCode: (phone: String) -> Unit,
//    onBack: () -> Unit
//) {
//
//    val context = LocalContext.current
//
//    val codeErrorState by vm.errorState.collectAsState()
//
//    BackHandler(
//        enabled = true,
//        onBack = {}
//    )
//
//    LaunchedEffect(vm.effect) {
//        vm.effect.collect {
//            when(it) {
//                is ConfirmCodeContract.Effect.NavigateToSecureCode -> openCreateSecureCode(phone)
//                is ConfirmCodeContract.Effect.OpenProfileScreen,
//                is ConfirmCodeContract.Effect.CouponSuccess -> onBack()
//                is ConfirmCodeContract.Effect.ShowToast ->
//                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        if (useCase == ConfirmCodeUseCase.PROFILE) {
//            vm.handleEvent(ConfirmCodeContract.Event.SendInitialProfileCode(phone))
//        }
//    }
//
//    val timerSecs = 60
//    var timer by remember { mutableIntStateOf(timerSecs) }
//
//    LaunchedEffect(Unit) {
//        while(true) {
//            delay(1000)
//            if (timer > 0)
//                timer -= 1
//        }
//    }
//
//    val captchaActivityLauncher =
//        rememberCaptchaActivityLauncher { result ->
//            if (result) {
//                timer = timerSecs
//                vm.handleEvent(ConfirmCodeContract.Event.SendSMSCode(phone))
//            } else {
//                Toast.makeText(
//                    /* context */ context,
//                    /* text */ context.getString(R.string.captcha_confirmation_failure),
//                    /* duration */ Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//
//    val callback =
//        object : AuthCodeCallback {
//            override fun onBack() = onBack()
//
//            override fun launchCaptchaConfirmation() {
//                val intent = Intent(context, HCaptchaActivity::class.java)
//                captchaActivityLauncher.launch(intent)
//            }
//
//            override fun resendCode() {
//                this.disableErrorState()
//                vm.handleEvent(ConfirmCodeContract.Event.SendSMSCode(phone))
//            }
//
//            override fun confirmCode(code: String) =
//                vm.handleEvent(
//                    ConfirmCodeContract.Event.ConfirmCode(phone, code, useCase)
//                )
//
//            override fun disableErrorState() =
//                vm.handleEvent(ConfirmCodeContract.Event.DisableErrorState)
//        }
//
//
//
//    Content(
//        errorState = codeErrorState,
//        resendTimerRemainedSecs = timer,
//        enableResendTimer = (useCase != ConfirmCodeUseCase.COUPON),
//        callback = callback
//    )
//
//}
//
//@Composable
//private fun Content(
//    errorState: Boolean,
//    resendTimerRemainedSecs: Int,
//    enableResendTimer: Boolean,
//    callback: AuthCodeCallback = EmptyAuthCodeCallback
//) {
//
//    val timerIsExpired = remember(resendTimerRemainedSecs) {
//        resendTimerRemainedSecs == 0
//    }
//
//    val code = rememberSaveable { mutableStateOf("") }
//
//    Scaffold(
//        topBar = {
//            TurnBackHeader(
//                label = stringResource(R.string.confirmation_code),
//                showArrow = false,
//            )
//        },
//        bottomBar = {
//            DigitalKeyboard(
//                onClickDigit = { digit ->
//                    if (code.value.length < 4) {
//                        code.value += digit
//                        if (code.value.length == 4) {
//                            callback.confirmCode(code.value)
//                        }
//                    }
//                },
//                onClickDelete = {
//                    code.value = code.value.dropLast(1)
//                    callback.disableErrorState()
//                },
//                onClickExit = callback::onBack
//            )
//        }
//    ) { scaffoldPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(scaffoldPadding)
//                .padding(24.dp),
//            verticalArrangement = Arrangement.spacedBy(48.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = stringResource(id = R.string.auth_code_title),
//                textAlign = TextAlign.Center,
//                color = MaterialTheme.colorScheme.secondary,
//                style = MaterialTheme.typography.headlineSmall,
//            )
//            AuthCodeInput(
//                code = code.value,
//                isErrorState = errorState
//            )
//            if (enableResendTimer) {
//                if (!timerIsExpired) {
//                    ResendCodeTimer(
//                        timerSecs = resendTimerRemainedSecs
//                    )
//                } else {
//                    ExpiredTimer(
//                        onClick = callback::launchCaptchaConfirmation
//                    )
//                }
//            }
//        }
//    }
//
//}
//
//@Composable
//private fun ResendCodeTimer(
//    timerSecs: Int,
//) {
//    Text(
//        text = stringResource(R.string.confirm_code_timer) + " ${timerSecs.convertSecsToClock()}",
//        style = MaterialTheme.typography.bodyLarge,
//        color = MaterialTheme.colorScheme.secondary.copy(0.4f),
//        textAlign = TextAlign.Center
//    )
//}
//
//@Composable
//private fun ExpiredTimer(
//    onClick: () -> Unit,
//) {
//    Text(
//        modifier = Modifier.clickable(
//            interactionSource = remember { MutableInteractionSource() },
//            indication = null,
//            onClick = onClick
//        ),
//        text = stringResource(R.string.request_sms_action),
//        style = MaterialTheme.typography.bodyLarge,
//        textAlign = TextAlign.Center
//    )
//}
//
//@Preview(showSystemUi = true, fontScale = 1.65f)
//@Preview(showSystemUi = true)
//@Composable
//private fun ConfirmCodeScreenPreview() {
//    MegahandTheme {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//        ) {
//            Content(
//                errorState = false,
//                enableResendTimer = true,
//                resendTimerRemainedSecs = 60
//            )
//        }
//    }
//}
//
//@Preview(showSystemUi = true)
//@Composable
//private fun ConfirmCodeErrorStatePreview() {
//    MegahandTheme {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//        ) {
//            Content(
//                errorState = true,
//                enableResendTimer = true,
//                resendTimerRemainedSecs = 60
//            )
//        }
//    }
//}