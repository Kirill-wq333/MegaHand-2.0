package com.evothings.mhand.presentation.feature.auth.ui.components.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.presentation.theme.MegahandTheme

@Preview
@Composable
private fun DigitalKeyboardPreview() {
    MegahandTheme {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            DigitalKeyboard(
                modifier = Modifier.padding(20.dp),
                onClickDigit = {},
                onClickDelete = {},
                onClickExit = {}
            )
        }
    }
}

@Composable
fun DigitalKeyboard(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClickDigit: (Int) -> Unit,
    onClickDelete: () -> Unit,
    onClickExit: () -> Unit
) {

    LazyVerticalGrid(
        modifier = modifier
            .alpha(
                alpha = if (isEnabled) 1.0f else 0.2f
            )
            .fillMaxWidth()
            .padding(
                vertical = 30.dp,
                horizontal = 70.dp
            ),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        items(12) { i ->
            when(i) {
                in 0..8, 10 -> {
                    // 10th index is a 0
                    val digit = if (i != 10) i + 1 else 0

                    DigitalKey(
                        item = digit,
                        onClick = { if (isEnabled) onClickDigit(digit) }
                    )
                }
                9 -> {
                    ExitTextButton(
                        onClick = onClickExit
                    )
                }
                11 -> {
                    DeleteIconButton(
                        onClick = { if (isEnabled) onClickDelete() }
                    )
                }
            }
        }

    }

}