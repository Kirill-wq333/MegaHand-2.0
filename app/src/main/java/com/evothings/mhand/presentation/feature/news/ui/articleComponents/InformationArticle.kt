package com.evothings.mhand.presentation.feature.news.ui.articleComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun InformationArticle(
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