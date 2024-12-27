package com.evothings.mhand.presentation.feature.onboarding.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings

@Composable
fun WelcomeOnboarding(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.intro_screen),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )
        TopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        )
        Column {
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
                    text = stringResource(R.string.turn_back),
                    textColor = ColorTokens.Graphite,
                    borderColor = ColorTokens.Graphite.copy(.1f),
                    onClick = {}
                )
                Button(
                    text = stringResource(R.string.next),
                    textColor = ColorTokens.Graphite,
                    backgroundColor = ColorTokens.Sunflower,
                    onClick = {}
                )
            }
        }
    }

}

@Composable
fun TopBar(
    modifier: Modifier = Modifier
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
       Box(
           modifier = Modifier
               .padding(MaterialTheme.paddings.extraLarge)
       ){
           Icon(
               imageVector = ImageVector.vectorResource(R.drawable.ic_close),
               contentDescription = null,
               modifier = Modifier
                   .padding(MaterialTheme.paddings.large)
           )
       }
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
    WelcomeOnboarding()
}