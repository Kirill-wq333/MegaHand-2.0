package com.evothings.mhand.presentation.testik

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Composable
fun StretchedBox() {
    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            rotation.animateTo(
                targetValue = 720f,
                animationSpec = tween(durationMillis = 1500),
                block = {
                    println("Current value: ${value}") // Вывод текущего значения на каждом кадре
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .graphicsLayer(
                alpha = 0.5f,
                scaleX = 0.1f, // Растягиваем по оси X
                scaleY = 1f, // Сжимаем по оси Y
                translationX = 111f,
                translationY = -215f,
                rotationZ = rotation.value,
                compositingStrategy = CompositingStrategy.ModulateAlpha
            )
            .background(Color.Blue)
    )
}

@Composable
fun StretchedBoxWithOffset() {
    Box(
        modifier = Modifier
            .size(200.dp)
    ) {
        // Левый верхний угол
        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(x = (-10).dp, y = (-30).dp)
                .background(Color.Red)
        )

        // Правый верхний угол
        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(x = 180.dp, y = (-10).dp)
                .background(Color.Green)
        )

        // Левый нижний угол
        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(x = (-30).dp, y = 160.dp)
                .background(Color.Yellow)
        )

        // Правый нижний угол
        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(x = 160.dp, y = 180.dp)
                .background(Color.Blue)
        )
    }
}

@Composable
fun DeformedBox() {
    Canvas(
        modifier = Modifier.size(200.dp)
    ) {
        val path = Path().apply {
            moveTo(50f, 15f) // Левый верхний угол
            lineTo(size.width * 0.5f, size.height * 0.039f) // Правый верхний угол (смещен)
            moveTo(50f, 15f)
            lineTo(size.width * 0.9f, size.height * 0.9f) // Правый нижний угол (смещен)
            moveTo(50f, 15f)
            lineTo(size.width * 0.1f, size.height * 0.45f) // Левый нижний угол (смещен)
            close()
        }

        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 4f)
        )
    }
}

@Preview
@Composable
private fun DeformedBoxPreview() {
    DeformedBox()
}

@Preview
@Composable
private fun StretchedBoxPreview() {
    StretchedBox()
}

@Preview
@Composable
private fun StretchedBoxWithOffsetPreview() {
    StretchedBoxWithOffset()
}