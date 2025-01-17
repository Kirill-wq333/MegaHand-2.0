package com.evothings.mhand.presentation.feature.onboarding.ui.screen

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.onboarding.viewmodel.IntroductionContract
import com.evothings.mhand.presentation.feature.onboarding.viewmodel.IntroductionViewModel
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings

private interface IntroductionCallback {
    fun onClose()
    fun onProceed()
}

@Composable
fun WelcomeOnboarding(
    modifier: Modifier = Modifier,
    vm: IntroductionViewModel,
    openMainScreen: () -> Unit
) {
    LaunchedEffect(vm.effect) {
        vm.effect.collect {
            when(it) {
                is IntroductionContract.Effect.OpenMainScreen -> openMainScreen()
            }
        }
    }
    Content(
        callback = object : IntroductionCallback {

            override fun onClose() =
                vm.handleEvent(IntroductionContract.Event.DeclineOnboarding)

            override fun onProceed() =
                vm.handleEvent(IntroductionContract.Event.Proceed)
        }
    )

}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    callback: IntroductionCallback
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.intro_screen),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            ColorTokens.Graphite
                        )
                    ),
                    alpha = 0.8f
                )
        )
        TopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            callback = callback
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            WelcomeOnboardingTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.paddings.extraGiant)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.paddings.extraGiant))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.paddings.extraGiant,
                        end = MaterialTheme.paddings.extraGiant,
                        bottom = MaterialTheme.paddings.extraGiant
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Button(
                    modifier = Modifier
                        .width(166.5.dp)
                        .height(44.dp),
                    text = stringResource(R.string.turn_back),
                    textColor = ColorTokens.Graphite,
                    backgroundColor = ColorTokens.White,
                    borderColor = ColorTokens.Graphite.copy(.1f),
                    onClick = callback::onClose
                )
                Button(
                    modifier = Modifier
                        .width(166.5.dp)
                        .height(44.dp),
                    text = stringResource(R.string.next),
                    textColor = ColorTokens.Graphite,
                    backgroundColor = ColorTokens.Sunflower,
                    onClick = callback::onProceed
                )
            }
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    callback: IntroductionCallback
) {

   Row(
       modifier = modifier,
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.SpaceBetween
   ) {
       Icon(
           imageVector = ImageVector.vectorResource(R.drawable.logo),
           contentDescription = null,
           tint = colorScheme.primary,
           modifier = Modifier
               .height(42.dp)
               .padding(MaterialTheme.paddings.extraLarge)
       )
       IconButton(
           icon = ImageVector.vectorResource(id = R.drawable.ic_close),
           tint = Color.White,
           onClick = callback::onClose
       )
   }

}

@Composable
fun WelcomeOnboardingTitle(
    modifier: Modifier = Modifier
) {
   Column(
       modifier = modifier,
       horizontalAlignment = Alignment.Start
   ) {
       Text(
           text = stringResource(R.string.tutorial_welcome),
           color = ColorTokens.White,
           style = MaterialTheme.typography.headlineLarge,
       )
       Spacer(modifier = Modifier.height(MaterialTheme.paddings.extraLarge))
       Text(
           text = stringResource(R.string.onboarding_description),
           color = ColorTokens.White,
           style = MaterialTheme.typography.bodyLarge,
       )
   }
}

@Preview
@Composable
private fun PreviewWelcomeOnboarding() {
    MegahandTheme(true) {
        Content(
            callback = object : IntroductionCallback {
                override fun onClose() {}
                override fun onProceed() {}
            }
        )
    }
}