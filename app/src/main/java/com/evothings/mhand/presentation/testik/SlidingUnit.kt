package com.evothings.mhand.presentation.testik

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.icon.SmallIconButton

@Preview
@Composable
private fun SlidingUnitPreview() {
    Surface {
        SlidingUnit()
    }
}

@Composable
fun SlidingUnit() {
    var openSubCatalog by remember { mutableStateOf(false) }
    var openCatalog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column {
            Row(
                modifier = Modifier
                    .clickable { openSubCatalog = !openSubCatalog },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                SmallIconButton(
                    onClick = {},
                    icon = if (openSubCatalog) ImageVector.vectorResource(R.drawable.ic_chevron_bottom)
                    else ImageVector.vectorResource(R.drawable.ic_chevron_right),
                )
                Text(
                    text = "kotlin+java",
                )
            }
            AnimatedVisibility(
                enter = fadeIn(animationSpec = tween(1500)) + slideIn(initialOffset = { IntOffset(-100, 0) }),
                exit = fadeOut(animationSpec = tween(1500)) + slideOut(targetOffset = { IntOffset(0, -50) }),
                modifier = Modifier
                    .offset(
                        x = 20.dp
                    ),
                visible = openSubCatalog,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    SmallIconButton(
                        onClick = {},
                        icon = if (openCatalog) ImageVector.vectorResource(R.drawable.ic_chevron_bottom)
                        else ImageVector.vectorResource(R.drawable.ic_chevron_right),
                    )
                    Text(
                        text = "com.evothing.mhand"
                    )
                }
            }
        }
    }

}