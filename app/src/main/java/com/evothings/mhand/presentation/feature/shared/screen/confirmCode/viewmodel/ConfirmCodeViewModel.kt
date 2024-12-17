package com.evothings.mhand.presentation.feature.shared.screen.confirmCode.viewmodel

//import androidx.lifecycle.viewModelScope
//import com.evothings.domain.feature.auth.interactor.AuthInteractor
//import com.evothings.domain.feature.coupon.interactor.CouponInteractor
//import com.evothings.domain.feature.profile.interactor.ProfileInteractor
//import com.evothings.mhand.core.viewmodel.BaseViewModel
//import com.evothings.mhand.presentation.feature.shared.screen.confirmCode.viewmodel.model.ConfirmCodeUseCase
//import com.evothings.mhand.presentation.feature.snackbar.host.SnackbarItemHost
//import com.evothings.mhand.presentation.feature.snackbar.model.SnackbarItem
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class ConfirmCodeViewModel @Inject constructor(
//    private val authInteractor: AuthInteractor,
//    private val profileInteractor: ProfileInteractor,
//    private val couponInteractor: CouponInteractor,
//    private val snackbarItemHost: SnackbarItemHost
//) : BaseViewModel<ConfirmCodeContract.Event, ConfirmCodeContract.State, ConfirmCodeContract.Effect>() {
//
//
//    private val _errorState = MutableStateFlow(false)
//    val errorState = _errorState.asStateFlow()
//
//    override fun setInitialState(): ConfirmCodeContract.State =
//        ConfirmCodeContract.State.InitialState
//
//    override fun handleEvent(event: ConfirmCodeContract.Event) = when(event) {
//        is ConfirmCodeContract.Event.ConfirmCode -> confirmCode(event.phone, event.code, event.type)
//        is ConfirmCodeContract.Event.DisableErrorState -> _errorState.update { false }
//        is ConfirmCodeContract.Event.SendSMSCode -> resendCode(event.phone)
//        is ConfirmCodeContract.Event.SendInitialProfileCode -> sendInitialProfileCode(event.phone)
//    }
//
//    private fun confirmCode(phone: String, code: String, useCase: ConfirmCodeUseCase) {
//        viewModelScope.launch(dispatcher) {
//            when(useCase) {
//                ConfirmCodeUseCase.AUTH -> confirmAuthCode(phone, code)
//                ConfirmCodeUseCase.PROFILE -> confirmChangeProfilePhone(phone, code)
//                ConfirmCodeUseCase.COUPON -> confirmCouponCode(phone, code)
//            }
//        }
//    }
//
//    private suspend fun confirmChangeProfilePhone(newPhone: String, code: String) {
//        profileInteractor.confirmChangePhone(code, newPhone)
//            .fold(
//                onSuccess = { changeNumberResult ->
//                     if (changeNumberResult) {
//                         setPhoneChangeSuccessSnackbar()
//                         setEffect { ConfirmCodeContract.Effect.OpenProfileScreen }
//                     } else {
//                         _errorState.emit(true)
//                     }
//                },
//                onFailure = {
//                    setEffect {
//                        ConfirmCodeContract.Effect.ShowToast(it.message.orEmpty())
//                    }
//                }
//            )
//    }
//
//    private suspend fun confirmAuthCode(phone: String, code: String) {
//        authInteractor.sendCode(phone, code)
//            .fold(
//                onSuccess = {
//                    setEffect { ConfirmCodeContract.Effect.NavigateToSecureCode }
//                },
//                onFailure = {
//                    _errorState.emit(true)
//                    if (it.message != null) {
//                        setEffect {
//                            ConfirmCodeContract.Effect.ShowToast(it.message.orEmpty())
//                        }
//                    }
//                }
//            )
//    }
//
//    private suspend fun confirmCouponCode(phone: String, code: String) {
//        couponInteractor.confirmCode(phone, code)
//            .fold(
//                onSuccess = {
//                    setCouponSuccessSnackbar()
//                    setEffect { ConfirmCodeContract.Effect.CouponSuccess }
//                },
//                onFailure = {
//                    _errorState.emit(true)
//                    if (it.message != null) {
//                        setEffect {
//                            ConfirmCodeContract.Effect.ShowToast(it.message.orEmpty())
//                        }
//                    }
//                }
//            )
//    }
//
//    private fun setCouponSuccessSnackbar() {
//        snackbarItemHost.setSnackbar(
//            SnackbarItem(
//                title = "Купон успешно оформлен!",
//                subtitle = "Ожидай СМС-сообщение"
//            )
//        )
//    }
//
//    private fun setPhoneChangeSuccessSnackbar() {
//        snackbarItemHost.setSnackbar(
//            SnackbarItem(
//                title = "Номер успешно изменен",
//                subtitle = ""
//            )
//        )
//    }
//
//    private fun sendInitialProfileCode(phone: String) {
//        viewModelScope.launch(dispatcher) {
//            profileInteractor.sendCodeOnNewPhone(phone)
//                .onFailure {
//                    setEffect {
//                        ConfirmCodeContract.Effect.ShowToast("Ошибка отправки кода")
//                    }
//                    setEffect { ConfirmCodeContract.Effect.OpenProfileScreen }
//                }
//        }
//    }
//
//    private fun resendCode(phone: String) {
//        viewModelScope.launch(dispatcher) {
//            authInteractor.requestSMS(phone)
//        }
//    }
//
//}