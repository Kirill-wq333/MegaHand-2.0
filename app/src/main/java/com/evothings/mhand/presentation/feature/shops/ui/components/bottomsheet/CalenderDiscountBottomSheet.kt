package com.evothings.mhand.presentation.feature.shops.ui.components.bottomsheet

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.shops.model.DiscountDay
import com.evothings.domain.feature.shops.model.enumeration.DiscountType
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale



@Composable
fun CalendarDiscountBottomSheet(
    modifier: Modifier = Modifier,
    days: PersistentList<DiscountDay>,
    onDismissRequest: () -> Unit
) {
    MhandModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        CalendarDiscount(
            days = days
        )
    }
}

@Composable
private fun CalendarDiscount(
    modifier: Modifier = Modifier,
    days: PersistentList<DiscountDay>,
) {
    val monthName = remember {
        val currentDate = LocalDate.now()
        val monthLowercase = currentDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())

        monthLowercase.replaceFirstChar { it.uppercase() }
    }

    val weeks = remember(days) { days.chunked(7) }

    var currentWeek by remember { mutableIntStateOf(0) }

    val weekDays = remember(currentWeek) {
        weeks[currentWeek].toPersistentList()
    }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .modalBottomSheetPadding(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(
                    id = R.string.discount_calendar_bottomsheet_title,
                    monthName,
                    currentWeek + 1
                ),
                color = colorScheme.secondary,
                style = MegahandTypography.titleLarge,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small)
            ) {
                weekDays.forEach { item ->
                    DiscountWeek(
                        number = item.dayOfMonth,
                        discount = item.discount,
                        dayWeek = item.dayOfWeek,
                        isToday = item.isToday,
                        hasAddition = item.hasAddition,
                        isActive = item.isActive,
                        type = item.type
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
                    onClick = {
                        val coercedWeek = (currentWeek - 1).coerceAtLeast(0)
                        currentWeek = coercedWeek
                    },
                    textColor = colorScheme.secondary,
                    borderColor = colorScheme.secondary.copy(.1f),
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
                Button(
                    modifier = Modifier
                        .weight(0.5f),
                    text = stringResource(R.string.next),
                    onClick = {
                        val coercedWeek = (currentWeek + 1).coerceAtMost(weeks.lastIndex)
                        currentWeek = coercedWeek
                    },
                    textColor = ColorTokens.Graphite,
                    backgroundColor = ColorTokens.Sunflower,
                )
            }
        }


}

@Composable
fun DiscountWeek(
    modifier: Modifier = Modifier,
    number: Int,
    dayWeek: String,
    isToday: Boolean,
    discount: String,
    isActive: Boolean,
    type: DiscountType,
    hasAddition: Boolean

) {

    val isDarkTheme = (colorScheme.secondary != ColorTokens.Graphite)

    val backgroundAlpha = remember(isToday, isDarkTheme) {
        when {
            isToday && isDarkTheme -> 0.2f
            isToday -> 1.0f
            else -> 0.05f
        }
    }

    val dayOfWeekAlpha = remember(isToday) { if (isToday) 1.0f else 0.4f }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = dayWeek,
            color = colorScheme.secondary.copy(dayOfWeekAlpha),
            style = MegahandTypography.bodyLarge,
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraMedium))
        DiscountDays(
            number = number,
            discount = discount,
            type = type,
            hasAddition = hasAddition,
            isToday = isToday,
            isActive = isActive
        )

    }

}

@Composable
fun DiscountDays(
    modifier: Modifier = Modifier,
    number: Int,
    discount: String,
    type: DiscountType,
    hasAddition: Boolean,
    isToday: Boolean,
    isActive: Boolean
) {


    val isDarkTheme = (colorScheme.secondary != ColorTokens.Graphite)
    val backgroundAlpha = remember(isToday, isDarkTheme) {
        when {
            isToday && isDarkTheme -> 0.2f
            isToday -> 1.0f
            else -> 0.05f
        }
    }
    val componentAlpha = remember(isActive) { if (isActive) 1.0f else 0.3f }
    val discountTextColor =
        if (isToday) colorScheme.primary else colorScheme.secondary

    val additionTextColor =
        if (isToday && !isDarkTheme) colorScheme.primary else colorScheme.error

    val dayOfMonthColor =
        when {
            isToday && !isDarkTheme -> colorScheme.onSecondary
            else -> colorScheme.secondary
        }

    Box(
        modifier = modifier
            .width(303.dp)
            .background(
                color = colorScheme.secondary.copy(backgroundAlpha),
                shape = MegahandShapes.medium
            )
            .alpha(componentAlpha)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.extraLarge),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = number.toString(),
                color = dayOfMonthColor,
                style = MegahandTypography.bodyLarge,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                if (hasAddition && type != DiscountType.NEW) {
                    Text(
                        text = stringResource(R.string.discount_addition),
                        color = ColorTokens.Red,
                        style = MegahandTypography.bodyLarge,
                    )

                    Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
                }
                when(type) {
                    DiscountType.NEW -> {
                        Text(
                            text = stringResource(R.string.discount_new),
                            color = additionTextColor,
                            style = MegahandTypography.titleLarge,
                        )
                    }
                    DiscountType.BY_PERCENTS -> {
                        Text(
                            text = "$discount%",
                            color = discountTextColor,
                            style = MegahandTypography.titleLarge,
                        )
                    }
                    DiscountType.ROUBLES -> {
                        Text(
                            text = "$discount ₽",
                            color = discountTextColor,
                            style = MegahandTypography.titleLarge,
                        )
                    }
                    DiscountType.BLACK_FRIDAY -> {
                        Text(
                            text = stringResource(R.string.discount_black_friday),
                            color = discountTextColor,
                            style = MegahandTypography.titleLarge,
                        )
                    }
                    DiscountType.TICKET -> {
                        Text(
                            text = stringResource(R.string.discount_ticket),
                            color = discountTextColor,
                            style = MegahandTypography.titleLarge,
                        )
                    }
                    DiscountType.WITHOUT_DISCOUNT -> {
                        Text(
                            text = stringResource(R.string.without_discount),
                            color = discountTextColor,
                            style = MegahandTypography.titleLarge,
                        )
                    }
                    DiscountType.NEW_DISCOUNT_SYSTEM -> {
                        Text(
                            text = stringResource(R.string.new_discount_system),
                            color = discountTextColor,
                            style = MegahandTypography.titleLarge,
                        )
                    }
                }
            }
        }
    }

}
