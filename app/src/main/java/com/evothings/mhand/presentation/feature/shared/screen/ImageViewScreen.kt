package com.evothings.mhand.presentation.feature.shared.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.evothings.mhand.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.MaterialTheme.colorScheme
import com.evothings.mhand.presentation.theme.MegahandTheme

@Preview
@Composable
private fun ImageViewScreenPreview() {
    MegahandTheme {
        ImageViewScreen(
            imageLink = "https://thumbs.dreamstime.com/z/blonde-woman-flowered-garden-beautiful-vertical-portrait-spring-wearing-big-white-hat-55489822.jpg"
        ) {}
    }
}

@Composable
internal fun ImageViewScreen(
    imageLink: String,
    onBack: () -> Unit
) {

    val minScale = 1.0f
    val maxScale = 5.0f

    var scale by remember { mutableFloatStateOf(1.0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTransformGestures(panZoomLock = true) { _, pan, zoom, _ ->

                    val maxOffsetX = (size.width * (scale - 1) / 2f).coerceAtLeast(0f)
                    val maxOffsetY = (size.height * (scale - 1) / 2f).coerceAtLeast(0f)

                    val newOffsetX = (offset.x + pan.x).coerceIn(-maxOffsetX, maxOffsetX)
                    val newOffsetY = (offset.y + pan.y).coerceIn(-maxOffsetY, maxOffsetY)

                    offset = Offset(newOffsetX, newOffsetY)
                    scale = (scale * zoom).coerceIn(minScale, maxScale)

                    if (scale == 1.0f)
                        offset = Offset(0f, 0f)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageLink),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
            tint = colorScheme.secondary,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.TopStart)
                .offset(x = 15.dp, y = 15.dp)
                .drawBehind {
                    drawCircle(
                        color = Color.White.copy(0.6f),
                        radius = size.width / 1.5f,
                    )
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onBack
                )
        )
    }

}