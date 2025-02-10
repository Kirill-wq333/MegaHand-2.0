package com.evothings.mhand.presentation.testik

import androidx.compose.runtime.Composable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.presentation.theme.MegahandTheme
import kotlinx.coroutines.launch

@Composable
fun AnimatedButton() {
    var buttonState by remember { mutableStateOf(ButtonState.NORMAL) }

    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val textOffsetX = remember { Animatable(0f) }
    val textOffsetY = remember { Animatable(0f) }

    val animatedColor = animateColorAsState(
        targetValue = when (buttonState) {
            ButtonState.NORMAL -> Color(0xFF3498db)
            ButtonState.PRESSED -> Color(0xFFe74c3c)
        },
        animationSpec = tween(durationMillis = 1500)
    )

    val text = when (buttonState) {
        ButtonState.NORMAL -> "Пришёл"
        ButtonState.PRESSED -> "Ушёл"
    }

    LaunchedEffect(buttonState) {
        when (buttonState) {
            ButtonState.PRESSED -> {
                launch {
                    offsetX.animateTo(
                        targetValue = -500f,
                        animationSpec = tween(durationMillis = 1500)
                    )
                    offsetY.animateTo(
                        targetValue = -700f,
                        animationSpec = tween(durationMillis = 1500)
                    )

                    textOffsetX.animateTo(
                        targetValue = 230f,
                        animationSpec = tween(durationMillis = 1000)
                    )
                    textOffsetY.animateTo(
                        targetValue = -400f,
                        animationSpec = tween(durationMillis = 1000)
                    )

                    kotlinx.coroutines.delay(1500)
                    buttonState = ButtonState.NORMAL
                }
            }

            ButtonState.NORMAL -> {
                launch {
                    offsetX.animateTo(
                        targetValue = 500f,
                        animationSpec = tween(durationMillis = 1500)
                    )
                    offsetY.animateTo(
                        targetValue = 700f,
                        animationSpec = tween(durationMillis = 1500)
                    )

                    textOffsetX.animateTo(
                        targetValue = -230f,
                        animationSpec = tween(durationMillis = 1000)
                    )
                    textOffsetY.animateTo(
                        targetValue = 400f,
                        animationSpec = tween(durationMillis = 1000)
                    )

                    kotlinx.coroutines.delay(1500)
                    buttonState = ButtonState.PRESSED
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.toInt(), offsetY.value.toInt()) }
            .clip(RectangleShape)
            .background(animatedColor.value)
            .clickable {
                if (buttonState == ButtonState.NORMAL) {
                    buttonState = ButtonState.PRESSED

                }else if (buttonState == ButtonState.PRESSED){
                    buttonState = ButtonState.NORMAL
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(color = Color.White, fontSize = 18.sp),
            modifier = Modifier.offset { IntOffset(textOffsetX.value.toInt(), textOffsetY.value.toInt()) }
        )
    }
}

enum class ButtonState {
    NORMAL,
    PRESSED
}

@Preview
@Composable
private fun AnimatedBoxPreview() {
    MegahandTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedButton()
        }
    }
}