package com.evothings.mhand.presentation.feature.home.ui.components

import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.values.MegahandShapes


@Composable
fun StoriesItem(
    storiesImage: Int,
    textStories: String
){
    Box(
        modifier = Modifier
            .size(96.dp)
            .border(width = 1.dp, color = colorScheme.primary, shape = MegahandShapes.medium),
        contentAlignment = Alignment.Center
    ){
        AsyncImage(
            model = storiesImage,
            contentDescription = "Stories",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(92.dp)
                .clip(shape = MegahandShapes.medium)
                .border(width = 1.dp, color = Color.White.copy(0.4f), shape = MegahandShapes.medium)
            )
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(MaterialTheme.paddings.medium),
            text = textStories,
            color = Color.White,
            lineHeight = 15.sp,
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily(listOf(Font(R.font.golos_400)))
        )
    }
}

@Preview
@Composable
fun PreviewStoriesItem(){
    MegahandTheme {
        StoriesItem(
            storiesImage = R.drawable.onboarding_story_1,
            textStories = "О магазинах Волгограда"
        )
    }
}

