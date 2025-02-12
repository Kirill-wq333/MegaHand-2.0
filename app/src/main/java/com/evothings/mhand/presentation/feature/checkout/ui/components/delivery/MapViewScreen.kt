package com.evothings.mhand.presentation.feature.checkout.ui.components.delivery

import android.app.Activity
import android.view.Window
import android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL
import android.view.WindowManager.LayoutParams.SCREEN_BRIGHTNESS_CHANGED
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.checkout.model.PickupPoint
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun MapViewScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onClose: () -> Unit,
    pickupPoints: List<PickupPoint>,
    pickupCity: String,
    selectedPickupPoint: PickupPoint,
    onChangePickupCity: (String) -> Unit,
    onSelectPickupPoint: (PickupPoint) -> Unit,
) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val window = (context as Activity).window
        val initialBrightness = window.attributes.screenBrightness
        window.setBrightness(BRIGHTNESS_OVERRIDE_FULL)

        onDispose {
            window.setBrightness(initialBrightness)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacers.extraLarge)
            .pointerInput(Unit) {
                detectTapGestures {}
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PickUpPoint(
            points = pickupPoints,
            city = pickupCity,
            selectedPoint = selectedPickupPoint,
            onChangeCity = onChangePickupCity,
            onSelectPoint = onSelectPickupPoint,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        Button(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            text = "Сохранить",
            textColor = ColorTokens.Graphite,
            backgroundColor = ColorTokens.Sunflower,
            onClick = onClose
        )
    }
}

private fun Window.setBrightness(value: Float) {
    this.apply {
        attributes.screenBrightness = value
        addFlags(SCREEN_BRIGHTNESS_CHANGED)
    }
}