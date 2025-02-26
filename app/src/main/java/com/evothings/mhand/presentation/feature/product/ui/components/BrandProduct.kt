package com.evothings.mhand.presentation.feature.product.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun BrandProduct(
    modifier: Modifier = Modifier,
    brand: String
){

    Box(
        modifier = modifier
            .width(100.dp)
            .background(color = colorScheme.onPrimary, shape = shapes.medium)
            .height(60.dp)
            .border(
                width = 1.dp,
                color = colorScheme.secondary.copy(0.05f),
                shape = MegahandShapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = brand,
            placeholder = painterResource(id = R.drawable.no_photo_placeholder),
            contentDescription = "brands",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(shape = shapes.medium)
                .width(50.dp)
                .height(30.dp)
        )
    }

}