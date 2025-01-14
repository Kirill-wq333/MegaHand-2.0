package com.evothings.mhand.presentation.feature.auth.ui.components.modifier

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

fun Modifier.shake(enabled: Boolean) = composed {
    if (!enabled) return@composed Modifier
    val shake = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        for (i in 0..3) {
            when (i % 2) {
                0 -> shake.animateTo(1f, spring(stiffness = 50_000f))
                else -> shake.animateTo(-1f, spring(stiffness = 50_000f))
            }
        }
        shake.animateTo(0f)
    }

    this.offset {
        IntOffset(
            (shake.value * 10).roundToInt(),
            0
        )
    }
}