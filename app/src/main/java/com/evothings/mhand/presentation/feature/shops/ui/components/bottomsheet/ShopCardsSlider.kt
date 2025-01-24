package com.evothings.mhand.presentation.feature.shops.ui.components.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.shops.model.Shop
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ShopCardsSlider(
    modifier: Modifier = Modifier,
    models: ImmutableList<Shop>,
    currentIndex: Int,
    onSelect: (Int) -> Unit,
    onOpenDiscountCalendar: (Int) -> Unit,
    onClickDial: (Int) -> Unit
) {
    val pagerState = rememberPagerState { models.size }

    LaunchedEffect(pagerState.currentPage) {
        onSelect(pagerState.currentPage)
    }

    LaunchedEffect(currentIndex) {
        pagerState.animateScrollToPage(currentIndex)
    }

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        pageSpacing = MaterialTheme.spacers.medium,
        pageSize = PageSize.Fixed(300.dp),
        key = { i -> models[i].address }
    ) { i ->
        val shopItem = remember { models[i] }
        ModalBottomSheetShop(
            street = shopItem.address,
            workingDays = shopItem.phone,
            onOpenDiscountCalendar = { onOpenDiscountCalendar(i) },
            onClickDial = { onClickDial(i) }
        )
    }
}



@Composable
fun ModalBottomSheetShop(
    street: String,
    workingDays: String,
    onOpenDiscountCalendar: () -> Unit,
    onClickDial: () -> Unit
) {

    Box(
        modifier = Modifier
            .background(
                color = colorScheme.onSecondary,
                shape = MegahandShapes.extraLarge
                )
    ) {

        Column(
            modifier = Modifier
                .padding(MaterialTheme.paddings.extraGiant)
        ) {
            Text(
                text = street,
                style = MegahandTypography.headlineMedium,
                color = colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Text(
                text = workingDays,
                style = MegahandTypography.bodyLarge,
                color = colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier
                        .width(198.dp)
                        .height(44.dp),
                    text = stringResource(R.string.discount_calendar),
                    textColor = colorScheme.secondary,
                    backgroundColor = colorScheme.primary,
                    onClick = onOpenDiscountCalendar
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
                Box(
                    modifier = Modifier
                        .background(
                            color = colorScheme.secondary.copy(.5f),
                            shape = MaterialTheme.shapes.medium
                        )
                        .clickable { onClickDial() }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_phone),
                        contentDescription = null,
                        tint = colorScheme.secondary,
                        modifier = Modifier
                            .padding(MaterialTheme.paddings.large)
                    )
                }
            }
        }

    }

}


@Preview
@Composable
private fun PreviewModalBottomSheetShop() {
    MegahandTheme(false) {
        ModalBottomSheetShop(
            street = "Street",
            workingDays = "9:00 - 21:00" +
                    "Discount on this day",
            onOpenDiscountCalendar = {},
            onClickDial = {}
        )
    }
}