package com.evothings.mhand.presentation.testik

import androidx.compose.runtime.Composable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.unit.sp
import com.evothings.mhand.presentation.theme.MegahandTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class ButtonState {
    NORMAL,
    PRESSED
}

@Composable
fun AnimatedBoxes2() {
    var buttonState by remember { mutableStateOf(ButtonState.NORMAL) }

    val boxCount = 4
    val boxSize = remember { List(boxCount) { Animatable(100f) } }
    val boxOffsetX = remember { List(boxCount) { Animatable(0f) } }
    val boxesSize = remember { Animatable(160f) }
    val spacerVertical = remember { Animatable(0f) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp


    LaunchedEffect(buttonState) {
        for (i in 0 until boxCount) {
            when (buttonState) {
                ButtonState.NORMAL ->
                    launch {
                        boxSize[i].animateTo(
                            targetValue = boxesSize.value,
                            animationSpec = tween(durationMillis = 1500)
                        )
                        boxOffsetX[i].animateTo(
                            targetValue = -110f + i * 20f + i * 200f - i + 115,
                            animationSpec = tween(durationMillis = 1500)
                        )
                        spacerVertical.animateTo(
                            targetValue = -25f,
                            animationSpec = tween(durationMillis = 1500)
                        )

                        delay(1500)
                        buttonState = ButtonState.PRESSED
                    }

                ButtonState.PRESSED ->
                    launch {
                        boxSize[i].animateTo(
                            targetValue = boxesSize.value,
                            animationSpec = tween(durationMillis = 1500)
                        )
                        boxOffsetX[i].animateTo(
                            targetValue = 725f + i * -20f + i * -200f - i - 55,
                            animationSpec = tween(durationMillis = 1500)
                        )
                        spacerVertical.animateTo(
                            targetValue = 25f,
                            animationSpec = tween(durationMillis = 1500)
                        )

                        delay(1500)
                        buttonState = ButtonState.NORMAL
                    }
            }
        }
    }

    Column(
        modifier = Modifier
            .width(screenWidth)
            .height(screenHeight),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(spacerVertical.value.dp))
        
        for (i in 0 until boxCount) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(boxOffsetX[i].value.toInt(), 0) }
                    .size(boxSize[i].value.dp)
                    .background(Color.Blue)
            )
        }
    }
}

@Preview
@Composable
private fun AnimatedBoxes2Preview() {
    Surface {
        AnimatedBoxes2()
    }
}

@Composable
fun AnimatedBoxes() {
    var buttonState by remember { mutableStateOf(ButtonState.NORMAL) }

    val boxCount = 4
    val boxSize = remember { List(boxCount) { Animatable(100f) } }
    val boxOffsetX = remember { List(boxCount) { Animatable(0f) } }
    val boxesSize = remember { Animatable(160f) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .width(screenWidth)
            .height(screenHeight),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Button(onClick = {
            buttonState = when (buttonState) {
                ButtonState.NORMAL -> ButtonState.PRESSED
                ButtonState.PRESSED -> ButtonState.NORMAL
            }

            coroutineScope.launch {
                for (i in 0 until boxCount) {
                    launch {
                        when (buttonState) {
                            ButtonState.NORMAL -> {
                                boxSize[i].animateTo(
                                    targetValue = boxesSize.value,
                                    animationSpec = tween(durationMillis = 1500)
                                )
                                boxOffsetX[i].animateTo(
                                    targetValue = -110f + i * 20f + i * 200f - i + 115,
                                    animationSpec = tween(durationMillis = 1500)
                                )
                            }

                            ButtonState.PRESSED -> {
                                boxSize[i].animateTo(
                                    targetValue = boxesSize.value,
                                    animationSpec = tween(durationMillis = 1500)
                                )
                                boxOffsetX[i].animateTo(
                                    targetValue = 730f + i * -20f + i * -200f - i - 55,
                                    animationSpec = tween(durationMillis = 1500)
                                )
                            }
                        }
                    }
                }
            }
        }
        ) {
            Text("Start Animation")
        }

        for (i in 0 until boxCount) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(boxOffsetX[i].value.toInt(), 0) }
                    .size(boxSize[i].value.dp)
                    .background(Color.Blue)
            )
        }
    }
}

@Preview
@Composable
private fun AnimatedBoxPreviews() {
    Surface {
        AnimatedBoxes()
    }
}

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

                    delay(1500)
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
                    delay(1500)
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

                } else if (buttonState == ButtonState.PRESSED) {
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