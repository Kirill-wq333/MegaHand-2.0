package com.evothings.mhand.presentation.testik

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Item(
    val id: Int,
    val name: String
)

@Preview
@Composable
private fun DraggableListPreview() {
    Surface {
        DraggableList()
    }
}

@Composable
fun DraggableList() {
    val items = remember { mutableStateListOf(
        Item(1, "Item 1"),
        Item(2, "Item 2"),
        Item(3, "Item 3"),
        Item(4, "Item 4"),
        Item(5, "Item 5")
    )}

    // Состояние для отслеживания текущего перетаскиваемого элемента
    val draggingItemId = remember { mutableStateOf<Int?>(null) }

    // Состояние для отслеживания смещения перетаскиваемого элемента
    val draggingOffset = remember { mutableStateOf(Offset.Zero) }

    // Состояние для отслеживания целевой позиции при перетаскивании
    val targetIndex = remember { mutableStateOf<Int?>(null) }

    // Состояние для хранения текущей позиции элемента (для анимации)
    val itemPositions = remember { mutableStateMapOf<Int, Offset>() }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            Modifier.fillMaxSize()
        ) {
            itemsIndexed(items) { index, item ->
                val itemPosition = itemPositions[item.id] ?: Offset.Zero // Получаем текущую позицию или 0, если её нет
                val animatedOffset = animateOffsetAsState(targetValue = itemPosition,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )

                DraggableItem(
                    item = item,
                    index = index,
                    items = items,
                    draggingItemId = draggingItemId,
                    draggingOffset = draggingOffset,
                    targetIndex = targetIndex,
                    animatedOffset = animatedOffset,
                    itemPositions = itemPositions
                )
            }
        }
    }
}

@Composable
fun DraggableItem(
    item: Item,
    index: Int,
    items: MutableList<Item>,
    draggingItemId: MutableState<Int?>,
    draggingOffset: MutableState<Offset>,
    targetIndex: MutableState<Int?>,
    itemPositions: MutableMap<Int, Offset>,
    animatedOffset: State<Offset>
) {
    var itemPosition = remember { mutableStateOf(Offset.Zero) }
    val isDragging = draggingItemId.value == item.id
    val zIndex = if (isDragging) 1f else 0f
    val itemHeight = 72.dp  // Предполагаемая высота элемента
    val itemHeightPx = with(LocalDensity.current) { itemHeight.toPx() } // Высота в пикселях
    val coroutineScope = rememberCoroutineScope()
    val itemOffsetY = animatedOffset.value.y

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .zIndex(zIndex)
            .offset { (IntOffset(0,itemOffsetY.toInt())) }
            .onGloballyPositioned { layoutCoordinates ->
                itemPosition.value = layoutCoordinates.positionInRoot()
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        draggingItemId.value = item.id
                        draggingOffset.value = offset
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        draggingOffset.value += Offset(dragAmount.x, dragAmount.y)

                        // Определяем целевую позицию
                        val newTargetIndex = calculateTargetIndex(
                            index,
                            draggingOffset.value.y,
                            items.size,
                            itemHeightPx
                        )
                        targetIndex.value = newTargetIndex
                    },
                    onDragEnd = {
                        draggingItemId.value = null
                        draggingOffset.value = Offset.Zero

                        // Перемещаем элементы, если целевая позиция отличается
                        if (targetIndex.value != null && targetIndex.value != index) {
                            val fromIndex = index
                            val toIndex = targetIndex.value!!

                            // Необходимо создать копию списка, чтобы обновить состояние
                            val newList = items.toMutableList()
                            val itemToMove = newList.removeAt(fromIndex)
                            newList.add(toIndex, itemToMove)
                            items.clear()
                            items.addAll(newList)

                            // Обновляем позиции элементов после перестановки
                            coroutineScope.launch {
                                itemPositions.clear()
                                // Пересчитываем позиции после небольшой задержки
                                delay(10) // Задержка для обновления макета
                                items.forEachIndexed { i, item ->
                                    itemPositions[item.id] = Offset(0f, (i * itemHeightPx))
                                }
                            }

                            targetIndex.value = null
                        } else {
                            // Анимируем возврат на исходную позицию
                            itemPositions[item.id] = Offset.Zero
                        }
                    },
                    onDragCancel = {
                        draggingItemId.value = null
                        draggingOffset.value = Offset.Zero
                        targetIndex.value = null
                    }
                )
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isDragging) 10.dp else 2.dp
        )
    ) {
        Text(
            text = item.name,
            modifier = Modifier.padding(16.dp)
        )
    }

    SideEffect {
        itemPositions[item.id] = Offset(0f, (index * itemHeightPx))
    }
}

fun calculateTargetIndex(
    currentIndex: Int,
    offsetY: Float,
    listSize: Int,
    itemHeight: Float // Высота элемента в пикселях
): Int? {
    val offsetIndex = (offsetY / itemHeight).toInt()
    val newIndex = (currentIndex + offsetIndex).coerceIn(0, listSize - 1)
    return newIndex
}