package com.evothings.mhand.presentation.feature.checkout.ui.components.delivery

import android.app.Activity
import android.view.Window
import android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL
import android.view.WindowManager.LayoutParams.SCREEN_BRIGHTNESS_CHANGED
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.checkout.model.PickupPoint
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
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
){
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val window = (context as Activity).window
        val initialBrightness = window.attributes.screenBrightness
        window.setBrightness(BRIGHTNESS_OVERRIDE_FULL)

        onDispose {
            window.setBrightness(initialBrightness)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacers.extraLarge),

    ) {
        PickUpPoint(
            points = pickupPoints,
            city = pickupCity,
            selectedPoint = selectedPickupPoint,
            onChangeCity = onChangePickupCity,
            onSelectPoint = onSelectPickupPoint,
        )
        IconButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_close),
            tint = ColorTokens.Graphite,
            backgroundColor = colorScheme.secondary,
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(0.dp, (-24).dp),
        )

    }
}

private fun Window.setBrightness(value: Float) {
    this.apply {
        attributes.screenBrightness = value
        addFlags(SCREEN_BRIGHTNESS_CHANGED)
    }
}