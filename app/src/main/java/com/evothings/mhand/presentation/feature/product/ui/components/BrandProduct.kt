package com.evothings.mhand.presentation.feature.product.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.evothings.mhand.presentation.theme.paddings

@Composable
fun BrandProduct(
    brand: String
){

    Box(
        modifier = Modifier
            .width(100.dp)
            .padding(start = MaterialTheme.paddings.extraLarge)
            .height(60.dp)
            .border(
                width = 1.dp,
                color = colorScheme.secondary.copy(0.05f),
                shape = shapes.medium
            )
    ) {
        AsyncImage(
            model = brand,
            contentDescription = "brands",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(shape = shapes.medium)
        )
    }

}