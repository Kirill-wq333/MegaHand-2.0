package com.evothings.mhand.presentation.feature.Category.components.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun CategoryItem(
    contentDescription: String?,
    painter: String,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = colorScheme.secondary.copy(0.05f),
                shape = MegahandShapes.medium
            )
            .size(121.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.TopStart
    ) {

        Image(
            painter = rememberAsyncImagePainter(model = painter),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(MegahandShapes.medium)
        )
        Text(
            text = text,
            color = colorScheme.secondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.paddings.large,
                    vertical = MaterialTheme.paddings.medium
                ),
        )

    }
}
