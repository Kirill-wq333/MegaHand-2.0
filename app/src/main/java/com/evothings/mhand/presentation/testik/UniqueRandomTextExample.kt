package com.evothings.mhand.presentation.testik

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import android.widget.ImageView
import androidx.compose.material3.Surface
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.compose.LocalImageLoader
import com.evothings.mhand.presentation.theme.MegahandTheme

@Suppress("UNUSED_EXPRESSION")
@Composable
fun GlideGifImage(gifUrl: String, contentDescription: String) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            ImageView(context).apply {
                Glide.with(context)
                    .asGif()
                    .load(gifUrl)
                    .into(this)
                contentDescription
            }
        },
        update = { imageView ->
            Glide.with(context)
                .asGif()
                .load(gifUrl)
                .into(imageView)
            imageView.contentDescription = contentDescription
        },
        modifier = Modifier
    )
}

@Preview
@Composable
fun Glide() {
    Surface {
        GlideGifImage(
            gifUrl = "https://i.pinimg.com/originals/b1/35/e4/b135e4c395dc5e1f5df5e36c9453f8d1.gif", // Замените на URL вашей GIF
            contentDescription = "Animated GIF" // Описание для доступности
        )
    }
}

@Composable
fun GifImage(gifUrl: String, contentDescription: String) {
    AsyncImage(
        model = gifUrl,
        contentDescription = contentDescription,
    )
}

@Preview
@Composable
fun Coin() {
    MegahandTheme {
        GifImage(
            gifUrl = "https://steamuserimages-a.akamaihd.net/ugc/2458480429364924457/2782F597092BEACF3F5841B27083608140969939/?imw=5000&imh=5000&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false", // Замените на URL вашей GIF
            contentDescription = "Animated GIF" // Описание для доступности
        )
    }
}