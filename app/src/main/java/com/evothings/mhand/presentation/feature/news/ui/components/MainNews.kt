package com.evothings.mhand.presentation.feature.news.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun MainNews(
    modifier: Modifier = Modifier,
    mainImage: String,
    title: String,
    publicationDate: String,
    informationNews: String,
    onShare: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(221.dp)
    ) {
        AsyncImage(
            model = mainImage,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(shape = MegahandShapes.extraLarge)
        )
        InformationMainNews(
            modifier = modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(MaterialTheme.paddings.giant),
            title = title,
            publicationDate = publicationDate,
            informationNews = informationNews,
            onClick = onShare
        )
    }

}

@Composable
fun InformationMainNews(
    modifier: Modifier = Modifier,
    title: String,
    publicationDate: String,
    informationNews: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                color = colorScheme.secondary.copy(.4f),
                style = MegahandTypography.bodyMedium
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.small))
            Text(
                text = publicationDate,
                color = colorScheme.secondary.copy(.4f),
                style = MegahandTypography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = informationNews,
                color = ColorTokens.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
                style = MegahandTypography.headlineMedium,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraLarge))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_share),
                contentDescription = null,
                tint = ColorTokens.White,
                modifier = Modifier
                    .clickable { onClick() }
            )
        }

    }
}


@Preview
@Composable
private fun PreviewMainNews() {
    MegahandTheme {
        MainNews(
            title = "Новость",
            mainImage = "https://s3-alpha-sig.figma.com/img/431b/470c/dd88f4e7511b4076f9d7748eddab16fc?Expires=1736121600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=foj0nwK~YDQZOU1FPzvF2KxBmp3S7Dt8EJfH31HqpfReSN3vRXL3kxxaVUFxPxD4~jX6qMzCVuw-hMGmEn6AzbgRJd7lArFjyH6J~bmxbmX1w57~zN-Z8WvgCKWFBKg3j9CG5afdmPOmO0jJ2z9UYbmg9d~PCFP9YYIdMr5OySWcr1xCtsNVyyhr6EI-UqUyGOSOA16SlJCCfF5VnWCidBeCX7NosMUTx3~DDitLoMz-TJj3TQczgl-5PAvYWQJRaK9d1qn3I6mLjms-AoIiKPIwG-JarguBhpuINiXRm9GVgA6GTjhJy5DNryDO3sEfmqeW~iAYhMAEP~jx~a4COw__",
            publicationDate = "5ч. назад",
            informationNews = "Стиль после 30 лет – как выглядеть шикарно и скромно одновременно",
            onShare = {}
        )
    }
}