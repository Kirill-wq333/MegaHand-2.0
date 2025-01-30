package com.evothings.mhand.presentation.feature

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SwipeToDeleteLeftRightList() {
    var items by remember { mutableStateOf(List(10) { "Item $it" }) }

    LazyColumn(
        modifier = Modifier
            .padding(20.dp)
    ) {
        items(items, key = { it }) { item ->
            SwipeToDeleteLeftRightItem(item = item, onDelete = { items = items.filterNot { it == item } })
        }
    }
}

@Composable
fun SwipeToDeleteLeftRightItem(item: String, onDelete: (String) -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }
    var swipeDirection by remember { mutableStateOf(SwipeDirection.None) }
    val animatedOffsetX by animateDpAsState(
        targetValue = when (swipeDirection) {
            SwipeDirection.Right -> 100.dp
            SwipeDirection.None -> offsetX.dp
        },
        animationSpec = tween(500)
    )
    var isRemoved by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    AnimatedVisibility(visible = !isRemoved, exit = fadeOut()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offsetX = 0f },
                        onDrag = { change, dragAmount ->
                            offsetX += dragAmount.x
                            change.consumePositionChange()
                        },
                        onDragEnd = {
                            if (offsetX > 50) {
                                swipeDirection = SwipeDirection.Right
                                coroutineScope.launch {
                                    delay(400)
                                    isRemoved = true
                                    onDelete(item)
                                }
                            } else {
                                swipeDirection = SwipeDirection.None
                                offsetX = 0f
                            }
                        }
                    )
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
                    .offset { IntOffset(animatedOffsetX.roundToPx(), 0) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item, modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    swipeDirection = SwipeDirection.Right
                    coroutineScope.launch {
                        delay(300)
                        isRemoved = true
                        onDelete(item)
                    }
                }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}


enum class SwipeDirection {
    None,
    Right
}

@Preview
@Composable
private fun SwipePreview() {
    Box(
        Modifier.fillMaxSize()
    ){
        SwipeToDeleteLeftRightList()
    }
}