package com.evothings.mhand.presentation.feature.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun BrandsItem(
    brands: String
) {

    Box(
        modifier = Modifier
            .width(100.dp)
            .background(color = colorScheme.onPrimary, shape = MegahandShapes.medium)
            .padding(start = MaterialTheme.paddings.extraGiant)
            .height(60.dp)
            .border(
                width = 1.dp,
                color = colorScheme.secondary.copy(0.05f),
                shape = MegahandShapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(30.dp)
        ) {
            AsyncImage(
                model = brands,
                contentDescription = "brands",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .matchParentSize()
            )
        }
    }

}

