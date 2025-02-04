package com.evothings.mhand.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private data class Particle(
    var position: Offset,
    var velocity: Offset,
    var color: Color,
    var size: Float
)

private fun createParticles(
    boxPosition: Offset,
    boxSize: Float,
    boxColor: Color,
    particleCount: Int,
    directions: List<Float>
): List<Particle> {
    val particles = mutableListOf<Particle>()
    val baseAngle = particleCount / directions.size // Распределяем частицы по направлениям


    for (direction in directions) {
        for (i in 0 until baseAngle) {
            val randomX = Random.nextFloat() * boxSize
            val randomY = Random.nextFloat() * boxSize
            val position = Offset(boxPosition.x + randomX, boxPosition.y + randomY)

            val angleVariation = 0f // +/- 30 градусов от базового угла
            val angle = (direction + (Random.nextFloat() * 200) * angleVariation).toRadians()
            val speed = Random.nextFloat() * 100 + 70 // Увеличил скорость

            val velocity = Offset(
                x = (speed * cos(angle)),
                y = (speed * sin(angle))
            )

            val size = Random.nextFloat() * 5 + 2
            particles.add(Particle(position, velocity, boxColor, size))
        }
    }
    return particles
}

private fun Float.toRadians(): Float {
    return Math.toRadians(this.toDouble()).toFloat()
}

@Preview
@Composable
private fun ExplodingPreview() {
    Surface {
        ExplodingBox(
            directions = listOf(90f)
        )
    }
}

@Composable
private fun ExplodingBox(directions: List<Float>) {
    var itemPosition = remember { mutableStateOf(Offset.Zero) }
    var isItemVisible by remember { mutableStateOf(true) }
    var boxSizeDp = 70.dp
    val boxSize = with(LocalDensity.current) { boxSizeDp.toPx() }
    val boxColor = Color.Blue

    var trashPosition = remember { mutableStateOf(Offset.Zero) }
    var trashSize = remember { mutableStateOf(Size.Zero) }

    var particles by remember { mutableStateOf(listOf<Particle>()) }
    val animationProgress = remember { Animatable(0f) }

    val initialPosition = remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(isItemVisible) {
        if (!isItemVisible) {
            particles = createParticles(itemPosition.value, boxSize, boxColor, 100, directions)
            animationProgress.snapTo(0f) // Сбрасываем анимацию
            animationProgress.animateTo(
                targetValue = 3f,
                animationSpec = tween(durationMillis = 5300, easing = LinearEasing)
            )
            itemPosition.value = initialPosition.value
            isItemVisible = true
        } else {
            animationProgress.snapTo(0f)
            particles = emptyList()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        verticalArrangement = Arrangement.spacedBy(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isItemVisible) {
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            itemPosition.value.x.toInt(),
                            itemPosition.value.y.toInt()
                        )
                    }
                    .size(boxSizeDp)
                    .background(boxColor)
                    .onGloballyPositioned { layoutCoordinates ->
                        if (itemPosition.value == Offset.Zero) {
                            itemPosition.value = layoutCoordinates.positionInRoot()
                            itemPosition.value = initialPosition.value
                        }
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {},
                            onDragEnd = {
                                val itemRect = Rect(itemPosition.value, Size(boxSize, boxSize))
                                val trashRect = Rect(trashPosition.value, trashSize.value)

                                if (itemRect.intersects(trashRect)) {
                                    isItemVisible = false
                                }
                            },
                            onDragCancel = {},
                            onDrag = { change, dragAmount ->
                                itemPosition.value += dragAmount
                            })
                    }
            )
        }

        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Red)
                .onGloballyPositioned { layoutCoordinates ->
                    trashPosition.value = layoutCoordinates.positionInRoot()
                    trashSize.value = layoutCoordinates.size.toSize()
                }
        )

        Canvas(modifier = Modifier.fillMaxSize()) {

            particles.forEach { particle ->
                val progress = animationProgress.value

                // Новая позиция частицы на основе анимации
                val newPosition = Offset(
                    x = particle.position.x + particle.velocity.x * progress - boxSize/2,
                    y = particle.position.y + particle.velocity.y * progress - boxSize/2
                )
                drawCircle(
                    color = particle.color,
                    radius = particle.size,
                    center = newPosition
                )
            }
        }
    }
}

private fun Any.intersects(trashRect: Rect): Boolean {
    return true
}