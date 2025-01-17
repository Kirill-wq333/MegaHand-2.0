package com.evothings.mhand.presentation.feature.home.ui.story.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun StoryTimeCounter(
    modifier: Modifier = Modifier,
    storiesCount: Int,
    currentStoryIndex: Int,
    watchPaused: Boolean,
    onTimerFinish: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        repeat(storiesCount) {
            TimerItem(
                modifier = Modifier.weight(0.1f),
                watched = it < currentStoryIndex,
                active = it == currentStoryIndex,
                paused = watchPaused,
                onFinishWatching = onTimerFinish,
            )
        }
    }

}

@Composable
private fun TimerItem(
    modifier: Modifier,
    watched: Boolean,
    active: Boolean,
    paused: Boolean,
    onFinishWatching: () -> Unit
) {

    val storyProgress = remember { Animatable(0f) }

    val remainingDuration by remember {
        derivedStateOf {
            val durationMillis = 7000
            val remainingPercents = 100 - (storyProgress.value * 100)
            (durationMillis / 100) * remainingPercents.roundToInt()
        }
    }

    LaunchedEffect(active) {
        if (active) {
            storyProgress.startAnimation(remainingDuration)
        } else {
            storyProgress.snapTo(0.0f)
        }
    }

    LaunchedEffect(paused) {
        if (paused) {
            storyProgress.stop()
        } else if (active) {
            storyProgress.startAnimation(remainingDuration)
        }
    }

    LaunchedEffect(storyProgress.value) {
        if (storyProgress.value == 1.0f) {
            onFinishWatching()
        }
    }

    Box(
        modifier = modifier
            .height(3.dp)
            .background(
                color = Color.White.copy(alpha = if (watched) 1.0f else 0.2f),
                shape = MaterialTheme.shapes.extraLarge
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(storyProgress.value)
                .height(3.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(8.dp)
                )
                .align(Alignment.CenterStart)
        )
    }

}

private suspend fun Animatable<Float, *>.startAnimation(duration: Int) {
    this.animateTo(
        targetValue = 1.0f,
        animationSpec = tween(
            durationMillis = duration,
            easing = LinearEasing
        )
    )
}
