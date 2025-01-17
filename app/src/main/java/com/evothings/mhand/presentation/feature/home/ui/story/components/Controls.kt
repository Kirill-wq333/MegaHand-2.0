package com.evothings.mhand.presentation.feature.home.ui.story.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Controls(
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
    onHold: () -> Unit,
    onRelease: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.3f)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClickLeft,
                    onLongClick = onHold
                )
                .releasePointer(onRelease)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.3f)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClickRight,
                    onLongClick = onHold
                )
                .releasePointer(onRelease)
        )
    }

}

private fun Modifier.releasePointer(onRelease: () -> Unit) = this then
        pointerInput(PointerEventType.Release) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    if (event.type == PointerEventType.Release) onRelease()
                }
            }
        }