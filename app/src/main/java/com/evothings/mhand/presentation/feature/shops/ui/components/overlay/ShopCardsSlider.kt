package com.evothings.mhand.presentation.feature.shops.ui.components.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.shops.model.Shop
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ShopCardsSlider(
    modifier: Modifier,
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

        ShopInfoCard(
            address = shopItem.address,
            workSchedule = shopItem.workSchedule,
            onOpenDiscountCalendar = { onOpenDiscountCalendar(i) },
            onClickDial = { onClickDial(i) }
        )
    }

}

@Composable
private fun ShopInfoCard(
    address: String,
    workSchedule: String,
    onOpenDiscountCalendar: () -> Unit,
    onClickDial: () -> Unit
) {
    Column(
        modifier = Modifier
            .requiredWidth(300.dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = MaterialTheme.shapes.medium
            )
            .padding(MaterialTheme.spacers.medium)
    ) {
        Text(
            text = address,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary,
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Text(
            text = workSchedule,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                text = stringResource(id = R.string.discount_calendar),
                backgroundColor = MaterialTheme.colorScheme.primary,
                textColor = ColorTokens.Graphite,
                onClick = onOpenDiscountCalendar,
                modifier = Modifier.weight(1f)
            )
            Spacer(
                modifier = Modifier
                    .width(MaterialTheme.spacers.medium)
            )
            IconButton(
                icon = ImageVector.vectorResource(id = R.drawable.ic_phone),
                tint = MaterialTheme.colorScheme.secondary,
                backgroundColor = MaterialTheme.colorScheme.secondary.copy(0.05f),
                onClick = onClickDial
            )
        }
    }
}