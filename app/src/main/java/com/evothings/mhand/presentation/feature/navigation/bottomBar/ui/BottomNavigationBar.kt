package com.evothings.mhand.presentation.feature.navigation.bottombar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.graphics.Color
import com.evothings.mhand.presentation.feature.navigation.bottombar.ui.bottomsheet.OtherBottomSheet
import com.evothings.mhand.presentation.feature.navigation.bottombar.ui.model.WebPageScreen
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.navigation.graph.Screen
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import kotlin.reflect.KClass

@Composable
fun BottomNavigationBar(
    currentRoute: KClass<*>,
    openScreen: (Screen) -> Unit,
    openWebPageScreen: (WebPageScreen) -> Unit,
    openPhoneConfirmationScreen: (String) -> Unit
) {

    val navEntries = remember { NavGraph.BottomNav.bottomNavigationEntries }
    var otherBottomSheetVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(colorScheme.onSecondary)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 36.dp,
                    vertical = 18.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navEntries.forEach { item ->

                // If current destination belongs to other or bottom nav entry selected
                val isSelected = remember(currentRoute) {
                    val isDestOther = NavGraph.otherDestinations.any { it::class == currentRoute }
                    val isItemOther = item == NavGraph.BottomNav.Other

                    (isDestOther && isItemOther) || currentRoute == item::class
                }

                NavigationItem(
                    isSelected = isSelected,
                    iconRes = item.iconResId,
                    onClick = {
                        if (item != NavGraph.BottomNav.Other) {
                            openScreen(item)
                        } else {
                            otherBottomSheetVisible = true
                        }
                    }
                )
            }
        }
    }

    if (otherBottomSheetVisible) {
        OtherBottomSheet(
            onDismissBottomSheet = { otherBottomSheetVisible = false },
            openAppScreen = openScreen,
            openWebPageScreen = openWebPageScreen,
            openPhoneConfirmationScreen = openPhoneConfirmationScreen,
        )
    }

}

@Composable
internal fun NavigationItem(
    isSelected: Boolean,
    iconRes: Int,
    onClick: () -> Unit,
) {

    val borderColor = if (isSelected) colorScheme.primary else Color.Transparent

    IconButton(
        icon = ImageVector.vectorResource(id = iconRes),
        tint = colorScheme.secondary,
        borderColor = borderColor,
        onClick = onClick
    )

}