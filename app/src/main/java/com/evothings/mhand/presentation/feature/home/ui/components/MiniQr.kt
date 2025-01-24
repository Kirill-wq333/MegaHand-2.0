package com.evothings.mhand.presentation.feature.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.loading.LoadingIndicator
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.values.MegahandShapes


@Composable
fun QrCode(
    modifier: Modifier = Modifier,
    qrLink: String,
    isOnboarding: Boolean,
    isOffline: Boolean = false,
    onClick: () -> Unit
) {

    val clickableModifier =
        Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )

    val density = LocalDensity.current
    val verticalPadding = remember {
        when(density.density) {
            in 1.5..2.0 -> 30.dp
            else -> 24.dp
        }
    }

    var isImageLoading by remember { mutableStateOf(false) }

    val background =
        if (isOffline) {
            colorScheme.secondary.copy(0.1f)
        } else {
            colorScheme.inverseSurface.copy(0.1f)
        }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = background,
                shape = shapes.large
                    .copy(
                        topStart = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp)
                    )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = clickableModifier
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp,
                    vertical = verticalPadding
                )
                .clip(shapes.large)
                .border(
                    width = 1.dp,
                    color = colorScheme.inverseSurface,
                    shape = shapes.large
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isOnboarding) {
                Image(
                    painter = painterResource(id = R.drawable.demo_qr_code),
                    contentDescription = null,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(MegahandShapes.large)
                )
                return
            }
            AsyncImage(
                model = qrLink,
                onState = { isImageLoading = it is AsyncImagePainter.State.Loading },
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .clip(MegahandShapes.large)
            )
            if (isImageLoading) {
                QRLoadingIndicator()
            }
        }

        Box(
            modifier = clickableModifier
                .align(Alignment.BottomEnd)
                .offset((-10).dp, (-10).dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = colorScheme.inverseSurface,
                    shape = CircleShape
                )
                .background(
                    color = colorScheme.onSecondary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_magnifying_glass),
                tint = colorScheme.secondary,
                contentDescription = null,
                modifier = Modifier.padding(6.dp)
            )
        }

    }
}

@Composable
private fun QRLoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        LoadingIndicator()
    }
}