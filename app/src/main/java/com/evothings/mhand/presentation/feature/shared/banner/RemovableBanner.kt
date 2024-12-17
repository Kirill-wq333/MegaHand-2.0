package com.evothings.mhand.presentation.feature.shared.banner

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun RemovableBanner(
    bannerLink: String,
    onClick: () -> Unit,
    onHide: () -> Unit
) {
    RemovableBanner(
        bannerPainter = rememberAsyncImagePainter(
            model = bannerLink,
            placeholder = painterResource(id = R.drawable.image_placeholder)
        ),
        onClick = onClick,
        onHide = onHide
    )
}

@Composable
fun RemovableBanner(
    @DrawableRes bannerResource: Int,
    onClick: () -> Unit,
    onHide: () -> Unit
) {
    RemovableBanner(
        bannerPainter = painterResource(id = bannerResource),
        onClick = onClick,
        onHide = onHide
    )
}

@Composable
private fun RemovableBanner(
    bannerPainter: Painter,
    onClick: () -> Unit,
    onHide: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacers.medium)
                .height(100.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
        ) {
            Image(
                painter = bannerPainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(MaterialTheme.shapes.large)
            )
            IconButton(
                icon = ImageVector.vectorResource(id = R.drawable.ic_close),
                tint = ColorTokens.Graphite,
                onClick = onHide,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        end = MaterialTheme.spacers.medium,
                        top = MaterialTheme.spacers.medium
                    )
            )
        }
    }

}

@Preview
@Composable
private fun RemovableBannerPreview() {
    MegahandTheme {
        Surface(color = Color.White) {
            RemovableBanner(
                bannerLink = "",
                onClick = {},
                onHide = {}
            )
        }
    }
}