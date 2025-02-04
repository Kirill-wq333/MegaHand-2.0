package com.evothings.mhand.presentation.feature.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.values.MegahandShapes


@Composable
fun StoriesItem(
    storiesImage: Int,
    textStories: String
) {
    Content(
        storiesImage = painterResource(id = storiesImage),
        textStories = textStories,
        onClickStory = {}
    )
}

@Composable
fun StoriesItems(
    storiesImage: String,
    textStories: String,
    onClickStory: () -> Unit
){

    val placeholder = painterResource(id = R.drawable.image_placeholder)

    val painter =
        rememberAsyncImagePainter(
            model = storiesImage,
            transform = { state ->
                when(state) {
                    is AsyncImagePainter.State.Error -> state.copy(painter = placeholder)
                    is AsyncImagePainter.State.Loading -> state.copy(painter = placeholder)
                    else -> state
                }
            }
        )
    Content(
        storiesImage = painter,
        textStories = textStories,
        onClickStory = onClickStory
    )
}

@Composable
private fun Content(
    storiesImage: Painter,
    textStories: String,
    onClickStory: () -> Unit
){

    Box(
        modifier = Modifier
            .size(96.dp)
            .border(width = 1.dp, color = colorScheme.primary, shape = MegahandShapes.medium)
            .clickable { onClickStory() },
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = storiesImage,
            contentDescription = "Stories",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(92.dp)
                .gradient()
                .clip(shape = MegahandShapes.medium)
                .border(width = 1.dp, color = Color.White.copy(0.4f), shape = MegahandShapes.medium)
            )
        Text(
            modifier = Modifier
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
            storiesImage =R.drawable.onboarding_story_1,
            textStories = "О магазинах Волгограда",
        )
    }
}

