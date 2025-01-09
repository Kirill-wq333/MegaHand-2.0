package com.evothings.mhand.presentation.feature.shops.ui.sheetShop

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

data class DiscountDay(
    val number: String,
    val dayWeek: String,
    val visible: Boolean,
    val title: String,
    val discount: String,
    val colorDiscount: Color,
    val colorDiscountDay: Color,
    val colorNumber: Color,
    val colorDayWeek: Color
)


@Composable
fun CalendarDiscountBottomSheet(
    modifier: Modifier = Modifier
) {
    val discountDays = listOf(
        DiscountDay(
            number = "1",
            dayWeek = "Пн",
            visible = false,
            title = "Добавление",
            discount = "10%",
            colorDiscount = colorScheme.secondary.copy(.2f),
            colorDiscountDay = colorScheme.secondary.copy(.02f),
            colorNumber = colorScheme.secondary.copy(.2f),
            colorDayWeek = colorScheme.secondary.copy(.2f)
        ),
        DiscountDay(
            number = "2",
            dayWeek = "Вт",
            visible = false,
            title = "Добавление",
            discount = "20%",
            colorDiscount = colorScheme.secondary.copy(.2f),
            colorDiscountDay = colorScheme.secondary.copy(.02f),
            colorNumber = colorScheme.secondary.copy(.2f),
            colorDayWeek = colorScheme.secondary.copy(.2f)
        ),
        DiscountDay(
            number = "3",
            dayWeek = "Ср",
            visible = true,
            title = "Добавление",
            discount = "30%",
            colorDiscount = colorScheme.primary,
            colorDiscountDay = colorScheme.secondary,
            colorNumber = colorScheme.onSecondary,
            colorDayWeek = colorScheme.secondary
        ),
        DiscountDay(
            number = "4",
            dayWeek = "Чт",
            visible = false,
            title = "Добавление",
            discount = "40%",
            colorDiscount = colorScheme.secondary,
            colorDiscountDay = colorScheme.secondary.copy(.05f),
            colorNumber = colorScheme.secondary,
            colorDayWeek = colorScheme.secondary.copy(.4f)
        ),
        DiscountDay(
            number = "5",
            dayWeek = "Пт",
            visible = false,
            title = "Добавление",
            discount = "50%",
            colorDiscount = colorScheme.secondary,
            colorDiscountDay = colorScheme.secondary.copy(.05f),
            colorNumber = colorScheme.secondary,
            colorDayWeek = colorScheme.secondary.copy(.4f)
        ),
        DiscountDay(
            number = "6",
            dayWeek = "Сб",
            visible = false,
            title = "Добавление",
            discount = "60%",
            colorDiscount = colorScheme.secondary,
            colorDiscountDay = colorScheme.secondary.copy(.05f),
            colorNumber = colorScheme.secondary,
            colorDayWeek = colorScheme.secondary.copy(.4f)
        ),
        DiscountDay(
            number = "7",
            dayWeek = "Вс",
            visible = false,
            title = "Добавление",
            discount = "70%",
            colorDiscount = colorScheme.secondary,
            colorDiscountDay = colorScheme.secondary.copy(.05f),
            colorNumber = colorScheme.secondary,
            colorDayWeek = colorScheme.secondary.copy(.4f)
        ),
        DiscountDay(
            number = "8",
            dayWeek = "Пн",
            visible = false,
            title = "Добавление",
            discount = "80%",
            colorDiscount = colorScheme.secondary,
            colorDiscountDay = colorScheme.secondary.copy(.05f),
            colorNumber = colorScheme.secondary,
            colorDayWeek = colorScheme.secondary.copy(.4f)
        ),
        DiscountDay(
            number = "9",
            dayWeek = "Вт",
            visible = false,
            title = "Добавление",
            discount = "90%",
            colorDiscount = colorScheme.secondary,
            colorDiscountDay = colorScheme.secondary.copy(.05f),
            colorNumber = colorScheme.secondary,
            colorDayWeek = colorScheme.secondary.copy(.4f)
        ),
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.onSecondary,
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp
                )
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.extraGiant),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Calendar discount",
                color = colorScheme.secondary,
                style = MegahandTypography.titleLarge,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small)
            ){
                items(discountDays) { item ->
                    DiscountWeek(
                        number = item.number,
                        dayWeek = item.dayWeek,
                        visible = item.visible,
                        discount = item.discount,
                        addendum = item.title,
                        colorDiscount = item.colorDiscount,
                        colorDiscountDay = item.colorDiscountDay,
                        colorNumber = item.colorNumber,
                        colorDayWeek = item.colorDayWeek
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraMedium))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Button(
                    modifier = Modifier
                        .weight(0.5f),
                    text = stringResource(R.string.turn_back),
                    onClick = {},
                    textColor = ColorTokens.Graphite,
                    backgroundColor = colorScheme.secondary.copy(.1f),
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
                Button(
                    modifier = Modifier
                        .weight(0.5f),
                    text = stringResource(R.string.next),
                    onClick = {},
                    textColor = ColorTokens.Graphite,
                    backgroundColor = ColorTokens.Sunflower,
                )
            }
        }
    }

}

@Composable
fun DiscountWeek(
    modifier: Modifier = Modifier,
    number: String,
    dayWeek: String,
    visible : Boolean,
    discount: String,
    addendum: String,
    colorDiscount: Color,
    colorDiscountDay: Color,
    colorNumber: Color,
    colorDayWeek: Color
) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = dayWeek,
            color = colorDayWeek,
            style = MegahandTypography.bodyLarge,
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraMedium))
        DiscountDay(
            number = number,
            visible = visible,
            discount = discount,
            addendum = addendum,
            colorDiscount = colorDiscount,
            colorDiscountDay = colorDiscountDay,
            colorNumber = colorNumber,
        )

    }

}

@Composable
fun DiscountDay(
    modifier: Modifier = Modifier,
    number: String,
    discount: String,
    visible : Boolean,
    addendum: String,
    colorDiscount: Color,
    colorDiscountDay: Color,
    colorNumber: Color,
) {

    Box(
        modifier = modifier
            .width(303.dp)
            .background(
                color = colorDiscountDay,
                shape = MegahandShapes.medium
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.extraLarge),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = number,
                color = colorNumber,
                style = MegahandTypography.bodyLarge,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                if (visible) {
                    Text(
                        text = addendum,
                        color = ColorTokens.Red,
                        style = MegahandTypography.bodyLarge,
                    )

                    Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
                }
                Text(
                    text = discount,
                    color = colorDiscount,
                    style = MegahandTypography.titleLarge,
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun CalendarDiscountBottomSheetPreview() {
    MegahandTheme(false) {
        CalendarDiscountBottomSheet()
    }
}