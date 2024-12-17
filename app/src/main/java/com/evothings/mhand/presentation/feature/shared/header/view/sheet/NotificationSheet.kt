package com.evothings.mhand.presentation.feature.shared.header.view.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

data class Notification(
    val icon: ImageVector,
    val heading: String,
    val underTheHeading: String,
    val gettingDate: String,
    val colorCircleShape: Color,
    val selected: Boolean,
    val contentDescription: String?,
    val color: Color
)


@Composable
fun Notification(
    textBottom: String,
) {
    val notification = listOf(
        Notification(
            icon = ImageVector.vectorResource(R.drawable.ic_sparkles),
            heading = "Новая версия приложения!",
            colorCircleShape = colorScheme.primary,
            underTheHeading = "2.1.10 Уже в App Store и Google Play",
            gettingDate = "3м. назад",
            selected = true,
            contentDescription = "sparkles",
            color = colorScheme.secondary
        ),
        Notification(
            icon = ImageVector.vectorResource(R.drawable.ic_news),
            heading = "Открытие магазина во Владивостоке",
            colorCircleShape = colorScheme.secondary.copy(0.1f),
            underTheHeading = "Читай подробнее в разделе «Новости»",
            gettingDate = "4ч. назад",
            selected = false,
            contentDescription = "news",
            color = colorScheme.secondary.copy(0.4f)
        ),
        Notification(
            icon = ImageVector.vectorResource(R.drawable.ic_exclamation),
            heading = "Сбой системы лояльности",
            colorCircleShape = colorScheme.secondary.copy(0.1f),
            underTheHeading = "Списание баллов временно недоступ...",
            gettingDate = "2ч. назад",
            selected = false,
            contentDescription = "exclamation",
            color = colorScheme.error
        )
    )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clip(shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
    ) {

        Column(
            modifier = Modifier
                .padding(
                    top = MaterialTheme.paddings.extraLarge,
                    start = MaterialTheme.paddings.extraLarge,
                    end = MaterialTheme.paddings.extraLarge
                ),
            horizontalAlignment = Alignment.End
        ) {

            LazyColumn() {
                items(notification) { notification ->
                    NotificationItem(
                        icon = notification.icon,
                        contentDescription = notification.contentDescription,
                        heading = notification.heading,
                        underTheHeading = notification.underTheHeading,
                        textBottom = textBottom,
                        gettingDate = notification.gettingDate,
                        colorCircleShape = notification.colorCircleShape,
                        selected = notification.selected,
                        color = notification.color
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
            Box(
                modifier = Modifier
                    .background(
                        color = colorScheme.secondary.copy(0.05f),
                        shape = shapes.small
                    )
                    .padding(bottom = MaterialTheme.paddings.extraLarge)
            ){
                Text(
                    text = stringResource(R.string.clear_all),
                    color = colorScheme.secondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
                    modifier = Modifier
                        .padding(
                            vertical = MaterialTheme.paddings.medium,
                            horizontal = MaterialTheme.paddings.large
                        )
                )
            }
        }

    }
}


@Composable
fun NotificationItem(
    icon: ImageVector,
    contentDescription: String?,
    heading: String,
    underTheHeading: String,
    textBottom: String,
    gettingDate: String,
    colorCircleShape: Color,
    selected: Boolean,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.paddings.extraLarge),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = colorCircleShape,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = color,
                modifier = Modifier
                    .padding(MaterialTheme.paddings.medium)
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
        Column(
            modifier = Modifier
                .width(230.dp)
        ) {
            TextItem(
                text = heading,
                color = colorScheme.secondary,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.tiny))
            TextItem(
                text = underTheHeading,
                color = colorScheme.secondary.copy(0.4f),
                fontSize = 12.sp
            )
            if (selected) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
                Box(
                    modifier = Modifier
                        .background(
                            color = colorScheme.primary,
                            shape = MegahandShapes.small
                        )
                ) {
                    Text(
                        text = textBottom,
                        color = colorScheme.secondary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
                        modifier = Modifier
                            .padding(
                                vertical = MaterialTheme.paddings.medium,
                                horizontal = MaterialTheme.paddings.large
                            )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
        TextItem(
            text = gettingDate,
            color = colorScheme.secondary.copy(0.4f),
            fontSize = 12.sp
        )
    }
}

@Composable
private fun TextItem(
    text: String,
    color: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.W400,
    fontFamily: FontFamily = FontFamily(listOf(Font(R.font.golos_400)))
){
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        lineHeight = 20.sp,
        fontWeight = fontWeight,
        fontFamily = fontFamily
    )
}


@Preview
@Composable
fun PreviewNotification() {
    MegahandTheme {
        Notification(
            textBottom = "Обновить",
        )
    }
}