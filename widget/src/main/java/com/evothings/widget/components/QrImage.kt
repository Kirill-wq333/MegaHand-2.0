package com.evothings.widget.components

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import com.evothings.widget.R
import androidx.glance.layout.ContentScale

@Composable
fun QrImage(
    modifier: GlanceModifier,
    contentScale: ContentScale = ContentScale.FillBounds,
    bitmap: Bitmap?
) {

    val provider = remember(bitmap) {
        if (bitmap != null)
            ImageProvider(bitmap)
        else
            ImageProvider(R.drawable.no_photo_placeholder)
    }

    Image(
        provider = provider,
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
    )
}