package com.evothings.mhand.presentation.feature.home.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.values.MegahandShapes


@Composable
fun StoriesItem(
    storiesImage: String,
    textStories: String,
    onClickStory: () -> Unit
){
    val placeholder = painterResource(id = R.drawable.image_placeholder)

    Box(
        modifier = Modifier
            .size(96.dp)
            .border(width = 1.dp, color = colorScheme.primary, shape = MegahandShapes.medium)
            .clickable { onClickStory() },
        contentAlignment = Alignment.Center
    ){
        AsyncImage(
            model = storiesImage,
            contentDescription = "Stories",
            contentScale = ContentScale.Crop,
            transform = { state ->
                when(state) {
                    is AsyncImagePainter.State.Error -> state.copy(painter = placeholder)
                    is AsyncImagePainter.State.Loading -> state.copy(painter = placeholder)
                    else -> state
                }
            },
            modifier = Modifier
                .size(92.dp)
                .gradient()
                .clip(shape = MegahandShapes.medium)
                .border(width = 1.dp, color = Color.White.copy(0.4f), shape = MegahandShapes.medium)
            )
        Text(
            modifier = Modifier
                .gradient()
                .align(Alignment.BottomStart)
                .padding(MaterialTheme.paddings.medium),
            text = textStories,
            color = Color.White,
            style = MegahandTypography.bodyMedium
        )
    }
}

private fun Modifier.gradient(): Modifier = this then
        drawWithContent {
            drawContent()
            drawRoundRect(
                brush = Brush.verticalGradient(
                    0.0f to Color(231, 213, 47, 0),
                    0.5f to Color(231, 213, 47, 50),
                    1.0f to Color(0xFF46432E)
                ),
                cornerRadius = CornerRadius(8f, 8f),
                alpha = 0.9f
            )
        }

@Preview
@Composable
fun PreviewStoriesItem(){
    MegahandTheme {
        StoriesItem(
            storiesImage = "",
            textStories = "О магазинах Волгограда",
            onClickStory = {}
        )
    }
}

