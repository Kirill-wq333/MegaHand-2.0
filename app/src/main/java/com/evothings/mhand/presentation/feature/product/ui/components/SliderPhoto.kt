package com.evothings.mhand.presentation.feature.product.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun SliderPhoto(
    model: List<String>
){
    LazyRow(
        modifier = Modifier
            .padding(start = MaterialTheme.paddings.extraLarge),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
    ) {
        items(4) {
            AsyncImage(
                model = model,
                placeholder = painterResource(id = R.drawable.no_photo_placeholder),
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = MegahandShapes.medium)
                    .size(345.dp)
            )
        }
    }
}