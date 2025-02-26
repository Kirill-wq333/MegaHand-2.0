package com.evothings.mhand.presentation.testik

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.drawscope.Fill

class ConvexShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(width * 0.2f, 0f)
            cubicTo(
                x1 = width * 0.3f, y1 = 0f,
                x2 = width * 0.3f, y2 = height * 0.2f,
                x3 = width * 0.5f, y3 = height * 0.2f
            )
            cubicTo(
                x1 = width * 0.7f, y1 = height * 0.2f,
                x2 = width * 0.7f, y2 = 0f,
                x3 = width * 0.8f, y3 = 0f
            )
            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun PerspectiveBox() {
    Canvas(modifier = Modifier.size(100.dp)) {
        val width = size.width
        val height = size.height

        val topLeft = Offset(width * 0.1f, height * 0.1f)
        val topRight = Offset(width * 0.9f, height * 0.1f)
        val bottomLeft = Offset(width * 0.1f, height * 0.9f)
        val bottomRight = Offset(width * 0.9f, height * 0.9f)

        val path = Path().apply {
            moveTo(topLeft.x, topLeft.y)
            cubicTo(
                x1 = width * 0.3f, y1 = 0f,
                x2 = width * 0.3f, y2 = height * 0.2f,
                x3 = width * 0.5f, y3 = height * 0.2f
            )
            cubicTo(
                x1 = width * 0.7f, y1 = height * 0.2f,
                x2 = width * 0.7f, y2 = 0f,
                x3 = width * 0.8f, y3 = 0f
            )
            lineTo(topRight.x, topRight.y)
            lineTo(bottomRight.x, bottomRight.y)
            lineTo(bottomLeft.x, bottomLeft.y)
            close()
        }

        drawPath(path = path, color = Color.Blue, style = Fill)
    }
}


@Composable
fun ClippedConvexBox() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(ConvexShape())
            .background(Color.Red)
    )
}

@Preview
@Composable
private fun PerspectiveBoxPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        PerspectiveBox()
    }
}


@Preview
@Composable
private fun ClippedConvexBoxPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        ClippedConvexBox()
    }
}