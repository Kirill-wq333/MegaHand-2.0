package com.evothings.mhand.presentation.testik

//import androidx.compose.animation.animateColor
//import androidx.compose.animation.core.animateShape
//import androidx.compose.animation.core.*
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp

//@Composable
//fun ComplexAnimationExample() {
//    var toggled by remember { mutableStateOf(false) }
//
//    // Определяем Transition, который будет управлять анимацией
//    val transition = updateTransition(targetState = toggled, label = "buttonTransition")
//
//    // Анимируем цвет фона
//    val backgroundColor by transition.animateColor(label = "color") { state ->
//        if (state) Color.Green else Color.Red
//    }
//
//    // Анимируем размер кнопки
//    val size by transition.animateDp(label = "size") { state ->
//        if (state) 200.dp else 100.dp
//    }
//
//    // Анимируем форму кнопки
//    val shape by transition.animateShape(label = "shape") { state ->
//        if (state) CircleShape else RoundedCornerShape(16.dp)
//    }
//
//    // Анимируем текст
//    val textSize by transition.animateFloat(label = "textSize") { state ->
//        if (state) 24f else 16f
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Button(
//            onClick = { toggled = !toggled },
//            modifier = Modifier
//                .size(size)
//                .background(backgroundColor, shape)
//        ) {
//            Text(
//                text = if (toggled) "On" else "Off",
//                fontSize = textSize.sp,
//                color = Color.White
//            )
//        }
//    }
//}