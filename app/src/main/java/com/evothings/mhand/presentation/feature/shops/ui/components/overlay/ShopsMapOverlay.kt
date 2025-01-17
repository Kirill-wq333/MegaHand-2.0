package com.evothings.mhand.presentation.feature.shops.ui.components.overlay

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.evothings.domain.feature.shops.model.Shop
import com.evothings.mhand.presentation.feature.shops.ui.sheetShop.CalendarDiscountBottomSheet
import com.evothings.mhand.presentation.theme.spacers
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ShopsMapOverlay(
    shops: ImmutableList<Shop>,
    currentShopIndex: Int,
    onSelect: (Int) -> Unit,
    onClickDial: (phone: String) -> Unit
) {

    var discountCalendarVisible by remember { mutableStateOf(false) }

    val shopItem = remember(currentShopIndex) {
        shops[currentShopIndex]
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ContactInfo(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(MaterialTheme.spacers.medium),
            email = "some@email.com",
            phone = shopItem.phone
        )
        ShopCardsSlider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(MaterialTheme.spacers.medium),
            models = shops,
            currentIndex = currentShopIndex,
            onSelect = onSelect,
            onOpenDiscountCalendar = { discountCalendarVisible = true },
            onClickDial = { onClickDial(shopItem.phone) }
        )
    }

    if (discountCalendarVisible) {
//        CalendarDiscountBottomSheet(
//            days = ,
//            onDismissRequest =
//        )
    }

}