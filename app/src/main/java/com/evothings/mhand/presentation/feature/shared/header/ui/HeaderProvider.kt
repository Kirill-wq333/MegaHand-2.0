package com.evothings.mhand.presentation.feature.shared.header.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HeaderProvider(
    modifier: Modifier = Modifier,
    isHomeScreen: Boolean = false,
    screenTitle: String,
    enableMapIconButton: Boolean = true,
    enableCardBalance: Boolean = true,
    enableNotificationButton: Boolean = true,
    onBack: () -> Unit,
    onChooseCity: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        content.invoke(PaddingValues(top = 70.dp))
        Header(
            modifier = modifier.fillMaxWidth(),
            nameCategory = screenTitle,
            logoVisible = isHomeScreen,
            locationVisible = enableMapIconButton,
            balanceVisible = enableCardBalance,
            notificationVisible = enableNotificationButton,
            onBack = onBack,
            onChooseCity = onChooseCity,
        )
    }
}