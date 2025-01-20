package com.evothings.mhand.presentation.feature.navigation.bottombar.ui.mock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.feature.navigation.bottombar.ui.BottomItem
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.navigation.graph.Screen
import com.evothings.mhand.presentation.theme.MegahandTheme

@Preview
@Composable
private fun MockNavBarPreview() {
    MegahandTheme {
        MockNavigationBar(
            selectedScreen = NavGraph.BottomNav.Home
        )
    }
}

@Composable
fun MockNavigationBar(selectedScreen: Screen) {
    Box(
        modifier = Modifier.fillMaxWidth(),
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
            NavGraph.BottomNav.bottomNavigationEntries.forEach { item ->
                BottomItem(
                    selected = selectedScreen == item::class,
                    icon = item.iconResId,
                    onClick = {}
                )
            }
        }
    }
}