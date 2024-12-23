package com.evothings.mhand.presentation.feature.navigation.bottomBar.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import kotlin.reflect.KClass


@Composable
fun BottomBarNavigation(
    currentRoute: KClass<*>,
) {
    val navEntries = remember { NavGraph.BottomNav.bottomNavigationEntries }

    navEntries.forEach { item ->
        val isSelected = remember(currentRoute) {
            val isDestOther = NavGraph.otherDestinations.any { it::class == currentRoute }
            val isItemOther = item == NavGraph.BottomNav.Other

            (isDestOther && isItemOther) || currentRoute == item::class
        }

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

                BottomItem(
                    selected = isSelected,
                    icon = item.iconResId,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
private fun BottomItem(
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
