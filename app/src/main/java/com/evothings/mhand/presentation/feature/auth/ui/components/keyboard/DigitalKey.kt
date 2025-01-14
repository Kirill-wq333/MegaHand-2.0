package com.evothings.mhand.presentation.feature.auth.ui.components.keyboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
//import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens

//private object DigitalKeyRippleTheme : RippleTheme {
//
//    @Composable
//    override fun defaultColor(): Color = ColorTokens.Sunflower
//
//    @Composable
//    override fun rippleAlpha(): RippleAlpha =
//        RippleAlpha(
//            pressedAlpha = .35f,
//            focusedAlpha = 0f,
//            hoveredAlpha = 0f,
//            draggedAlpha = 0f
//        )
//
//}
//
//@Composable
//fun DigitalKey(
//    item: Int,
//    onClick: () -> Unit
//) {
//
//    CompositionLocalProvider(
//        value = LocalRippleTheme provides DigitalKeyRippleTheme
//    ) {
//        Box(
//            modifier = Modifier
//                .size(60.dp)
//                .clip(MaterialTheme.shapes.medium)
//                .clickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = rememberRipple(
//                        bounded = true,
//                        color = ColorTokens.Sunflower
//                    ),
//                    onClick = onClick
//                ),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = item.toString(),
//                style = MaterialTheme.typography.headlineLarge
//            )
//        }
//    }
//
//}