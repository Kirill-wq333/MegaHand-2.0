package com.evothings.mhand.presentation.feature.onboarding.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.bottomsheet.Other
import com.evothings.mhand.presentation.feature.onboarding.model.CardAlignment
import com.evothings.mhand.presentation.feature.onboarding.model.Onboarding
import com.evothings.mhand.presentation.feature.onboarding.ui.components.BottomSheetLikeIndicator
import com.evothings.mhand.presentation.feature.onboarding.ui.components.OnboardingScrim
import com.evothings.mhand.presentation.feature.onboarding.ui.components.Onboardings
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun OtherOnboardingScreen(onFinish: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        OnboardingScrim(onClick = onFinish)

        with(Onboarding.Other.BottomSheet) {
            Onboardings(
                icon = iconRes,
                heading = title,
                underHeading = description,
                onFinish = true,
                alignment = CardAlignment.TOP,
                visibleButtonTurnBack = false,
                pageNumber = itemsCounter,
                onClickNext = onFinish,
                onClickTurnBack = {}
            )
        }

        DemoOtherBottomSheet(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}

@Composable
private fun DemoOtherBottomSheet(modifier: Modifier) {

    val isDarkMode = MaterialTheme.colorScheme.secondary != ColorTokens.Graphite

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            ),
    ) {
        BottomSheetLikeIndicator(
            modifier = Modifier.align(Alignment.TopStart)
        )
        Other(
            modifier = Modifier
                .padding(
                    vertical = 15.dp,
                    horizontal = MaterialTheme.spacers.extraLarge
                ),
            couponAmount = 300,
            darkMode = isDarkMode,
            enableCouponButton = true,
            openAppScreen = {},
            openVacanciesPage = {},
            openHelpPage = {},
            onChangeTheme = {},
            openCouponPhoneConfirmationScreen = {}
        )
    }

}

@Preview
@Composable
private fun OtherOnboardingPreview() {
    MegahandTheme {
        Surface {
            OtherOnboardingScreen(
                onFinish = {}
            )
        }
    }
}