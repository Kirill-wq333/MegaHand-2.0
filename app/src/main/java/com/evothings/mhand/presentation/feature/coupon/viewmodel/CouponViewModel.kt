package com.evothings.mhand.presentation.feature.coupon.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.coupon.interactor.CouponInteractor
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.evothings.domain.feature.coupon.model.CouponForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(
    private val couponInteractor: CouponInteractor
) : BaseViewModel<CouponContract.Event, CouponContract.State, CouponContract.Effect>() {


    private val _bonusAmount = MutableStateFlow(0)
    val bonusAmount = _bonusAmount.asStateFlow()

    init {
        getCouponBonusAmount()
    }

    private fun getCouponBonusAmount() {
        viewModelScope.launch(dispatcher) {
            _bonusAmount.emit(couponInteractor.getCouponAmount())
        }
    }

    override fun setInitialState(): CouponContract.State = CouponContract.State.Idle

    override fun handleEvent(event: CouponContract.Event) {
        if (event is CouponContract.Event.SendForm) {
            sendCouponForm(event.form)
        }
    }

    private fun sendCouponForm(form: CouponForm) {
        viewModelScope.launch(dispatcher) {
            couponInteractor.sendForm(form)
                .fold(
                    onSuccess = {
                        setEffect { CouponContract.Effect.OpenConfirmationScreen(form.phone) }
                    },
                    onFailure = {
                        setEffect { CouponContract.Effect.ShowToast("Ошибка: ${it.message}") }
                    }
                )
        }
    }

}