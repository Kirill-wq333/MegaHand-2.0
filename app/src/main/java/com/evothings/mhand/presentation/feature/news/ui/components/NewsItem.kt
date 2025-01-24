package com.evothings.mhand.presentation.feature.news.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    imageLink: String,
    publicationDate: String,
    information: String,
    category: String,
    onClick: () -> Unit
) {
    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.paddings.extraLarge)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = information,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MegahandTypography.headlineSmall,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
                Row {
                    Text(
                        text = category,
                        color = colorScheme.secondary.copy(.4f),
                        style = MegahandTypography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
                    Text(
                        text = publicationDate,
                        color = colorScheme.secondary.copy(.4f),
                        style = MegahandTypography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
            AsyncImage(
                model = imageLink,
                placeholder = painterResource(id = R.drawable.no_photo_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(61.dp)
                    .clip(shape = MegahandShapes.extraLarge)
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorScheme.secondary.copy(.05f))
        )
    }
}
