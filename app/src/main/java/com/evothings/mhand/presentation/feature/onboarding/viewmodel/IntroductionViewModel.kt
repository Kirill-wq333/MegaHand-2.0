package com.evothings.mhand.presentation.feature.onboarding.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.onboarding.interactor.OnboardingInteractor
import com.evothings.mhand.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val onboardingInteractor: OnboardingInteractor
) : BaseViewModel<IntroductionContract.Event, IntroductionContract.State, IntroductionContract.Effect>() {

    override fun setInitialState(): IntroductionContract.State = IntroductionContract.State.Idle

    override fun handleEvent(event: IntroductionContract.Event) {
        viewModelScope.launch(dispatcher) {
            onboardingInteractor.setIntroScreenShown()
            when(event) {
                is IntroductionContract.Event.DeclineOnboarding ->
                    onboardingInteractor.disableAllOnboardings()
                is IntroductionContract.Event.Proceed -> {}
            }
            setEffect { IntroductionContract.Effect.OpenMainScreen }
        }
    }

}