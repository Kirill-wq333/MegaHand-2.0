package com.evothings.mhand.presentation.feature.catalog.ui.components.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun CategoryItemOnboarding(
    modifier: Modifier = Modifier,
    title: String,
    image: Int,
    onClick: () -> Unit
) {
    Item(
        painter = painterResource(image),
        contentDescription = null,
        text = title,
        onClick = onClick
    )
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    onClick: () -> Unit
) {
   Item(
       painter = rememberAsyncImagePainter(image),
       contentDescription = null,
       text = title,
       onClick = onClick
   )
}

@Composable
fun Item(
    contentDescription: String?,
    painter: Painter,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = colorScheme.secondary.copy(0.05f),
                shape = MegahandShapes.medium
            )
            .size(121.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.TopStart
    ) {

        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(MegahandShapes.medium)
        )
        Text(
            text = text,
            color = colorScheme.secondary,
            style = MegahandTypography.labelLarge,
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.paddings.large,
                    vertical = MaterialTheme.paddings.medium
                ),
        )

    }
}
