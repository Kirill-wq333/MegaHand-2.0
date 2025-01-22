package com.evothings.mhand.presentation.feature.home.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.evothings.mhand.R

@Composable
fun BrandsItem(
    modifier: Modifier = Modifier,
    brands: String
) {

    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .apply {
            if (brands.takeLast(3) == "svg") {
                decoderFactory(SvgDecoder.Factory())
            }
            data(brands)
        }.build()

    AsyncImage(
        model = imageRequest,
        placeholder = painterResource(id = R.drawable.image_placeholder),
        contentScale = ContentScale.Fit,
        contentDescription = null,
        modifier = modifier
            .size(
                width = 100.dp,
                height = 60.dp
            )
            .clip(MaterialTheme.shapes.large)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary.copy(0.05f),
                shape = MaterialTheme.shapes.large
            )
    )

}

