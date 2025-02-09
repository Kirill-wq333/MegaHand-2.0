package com.evothings.mhand.presentation.feature.navigation.bottomBar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.bottomsheet.OtherBottomSheet
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.model.WebPageScreen
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.navigation.graph.Screen
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.theme.paddings
import kotlin.reflect.KClass


@Composable
fun BottomBarNavigation(
    currentRoute: KClass<*>,
    openScreen: (Screen) -> Unit,
    openWebPageScreen: (WebPageScreen) -> Unit,
    openPhoneConfirmationScreen: (String) -> Unit
) {
    val navEntries = remember { NavGraph.BottomNav.bottomNavigationEntries }
    var otherBottomSheetVisible by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = MaterialTheme.paddings.giant),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            navEntries.forEach { item ->
                val isSelected = remember(currentRoute) {
                    val isDestOther = NavGraph.otherDestinations.any { it::class == currentRoute }
                    val isItemOther = item == NavGraph.BottomNav.Other

                    (isDestOther && isItemOther) || currentRoute == item::class
                }
                BottomItem(
                    selected = isSelected,
                    icon = item.iconResId,
                    onClick = {
                        if(item != NavGraph.BottomNav.Other){
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
internal fun BottomItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: Int
) {
    val borderColor = if (selected) colorScheme.primary else Color.Transparent


    IconButton(
        icon = ImageVector.vectorResource(id = icon),
        tint = colorScheme.secondary,
        borderColor = borderColor,
        onClick = onClick
    )
}
