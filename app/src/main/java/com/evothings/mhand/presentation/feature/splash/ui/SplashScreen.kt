package com.evothings.mhand.presentation.feature.splash.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.splash.viewmodel.SplashContract
import com.evothings.mhand.presentation.feature.splash.viewmodel.SplashViewModel
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@SuppressLint("InlinedApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SplashScreen(
    vm: SplashViewModel,
    openMainScreen: () -> Unit,
    openOnboardingIntro: () -> Unit,
) {
    val state = vm.state.collectAsStateWithLifecycle()
    val enableDelayText by vm.enableDelayText.collectAsState()

    val notificationPermission = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!notificationPermission.status.isGranted) {
                notificationPermission.launchPermissionRequest()
            }
        }
    }

    LaunchedEffect(Unit) {
        vm.handleEvent(SplashContract.Event.DetermineNavRoute)
    }

    LaunchedEffect(vm.effect) {
        vm.effect.collect { effect ->
            when (effect) {
                is SplashContract.Effect.NavigateToMain -> openMainScreen()
                is SplashContract.Effect.NavigateToOnboarding -> openOnboardingIntro()
                else -> SplashContract.Effect.NavigateToTechWorks
            }
        }
    }

    when (state.value) {
        is SplashContract.State.Initial -> Content(enableDelayText)
        is SplashContract.State.TechWorks -> LoadingTechnicalServiceScreen()
    }
}

@Composable
private fun Content(
    enableDelayText: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorTokens.Graphite),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo),
                contentDescription = null
            )
            Spacer(
                modifier = Modifier.height(150.dp)
            )
            CircularProgressIndicator(
                modifier = Modifier.size(36.dp),
                color = colorScheme.primary,
                strokeWidth = 3.dp,
            )
        }
        AnimatedVisibility(
            visible = enableDelayText,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
        ) {
            Text(
                text = stringResource(R.string.splash_screen_delay_text),
                style = typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@Composable
fun LoadingTechnicalServiceScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFF46423E)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_setting),
                contentDescription = "Logo",
                tint = colorScheme.primary,
                modifier = Modifier
                    .size(128.dp)
            )
            Spacer(modifier = Modifier.height(192.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(28.dp),
                color = colorScheme.primary,
                strokeWidth = 2.dp,
                trackColor = colorScheme.secondary.copy(0.6f)
            )
            Spacer(modifier = Modifier.height(90.dp))
            Text(
                text = stringResource(R.string.server_tech_works),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily(listOf(Font(R.font.golos_500)))
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.app_unavailable),
                fontSize = 16.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                modifier = Modifier
                    .padding(horizontal = 70.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    MegahandTheme {
        Content(false)
    }
}



