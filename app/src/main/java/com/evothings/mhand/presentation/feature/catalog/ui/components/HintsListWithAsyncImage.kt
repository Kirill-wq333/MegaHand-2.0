package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun HintsListAsyncImage(
    model: String,
    contentDescription: String?,
    nameCategory: String,
    descriptionCategory: String,
    onClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorScheme.onSecondary)
                .clickable { onClick() }
                .padding(MaterialTheme.paddings.extraLarge),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = model,
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .clip(shape = MegahandShapes.extraLarge)
                        .size(54.dp)
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = nameCategory,
                        color = colorScheme.secondary,
                        style = MegahandTypography.headlineSmall
                    )
                    Text(
                        text = descriptionCategory,
                        color = colorScheme.secondary.copy(0.6f),
                        style = MegahandTypography.bodyLarge
                    )
                }
            }
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_right),
                contentDescription = null,
                tint = colorScheme.secondary
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorScheme.secondary.copy(0.05f))
        )
    }

}


@Preview
@Composable
fun PreviewHintsListAsyncImage(){
    MegahandTheme {
        HintsListAsyncImage(
            model = "https://s3-alpha-sig.figma.com/img/bbc9/b4f4/3bcb0ac3ea5a25f8bff886fa37da5c5d?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=AQyMhZk8IhlCmT-0PnG5aoBYVuy~QU7XpvBX-zmheXo2yABCgT18nTSVlhQl3CkzxGcYl76a2mNMvFZI4-r6lHfd65DnBkRiCRkEACHRekl7AlIEHN6u2NftRCb53RWgs1sEYVQPw72e4mjR5PYQGMFroWxnhQ2cqYuERt7DlbEAh73hw2GWau5fx5m~6eDPs2h7MR5z3x07un84o7AIJ~Jwo6p-EwD9OmrGLCj4su1zIi3oZNzOKwJX0bbfZx571gxUejCZLRkw0vxL~AMa7EAL2ywi-UpROw0zbEM8xWrE~MzQhriBATrIAU1tEbjx9iP2tWq2V6FzS1zy9UTRqw__",
            contentDescription = "clothes",
            nameCategory = "Одежда",
            descriptionCategory = "Одежда – Верхняя одежда",
            onClick = {}
        )
    }
}