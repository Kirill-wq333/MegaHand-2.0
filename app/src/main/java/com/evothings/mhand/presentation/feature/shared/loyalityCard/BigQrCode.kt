package com.evothings.mhand.presentation.feature.shared.loyalityCard

import android.app.Activity
import android.view.Window
import android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL
import android.view.WindowManager.LayoutParams.SCREEN_BRIGHTNESS_CHANGED
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton

@Composable
fun BigQrcode(
    modifier: Modifier = Modifier,
    qrCodeLink: String?,
    onClose: () -> Unit
) {
    if (qrCodeLink == null) return

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
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.5f))
    ) {
        AsyncImage(
            model = qrCodeLink,
            placeholder = painterResource(id = R.drawable.image_placeholder),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center)
                .clip(shapes.large)
                .border(
                    width = 1.dp,
                    color = colorScheme.inverseSurface,
                    shape = shapes.large
                )
        )

        IconButton(
            icon = ImageVector.vectorResource(R.drawable.ic_close),
            tint = colorScheme.secondary,
            backgroundColor = colorScheme.onSecondary,
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset((-24).dp, 24.dp)
        )
    }

}

private fun Window.setBrightness(value: Float) {
    this.apply {
        attributes.screenBrightness = value
        addFlags(SCREEN_BRIGHTNESS_CHANGED)
    }
}