package com.evothings.mhand.presentation.feature.onboarding.ui.components

import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.feature.onboarding.model.Onboarding

/*
 * This modifier is used to mark items that is highlight on onboarding screen
 * Highlight stands for hiding element and then appearing more bright one on gray screen
 */

@Stable
@Composable
fun Modifier.onboardingItem(
    itemsMap: MutableMap<Onboarding, Float>,
    key: Onboarding,
    hide: Boolean
): Modifier {
    val density = LocalDensity.current
    return this then
            onGloballyPositioned { layoutCoordinates ->
                itemsMap[key] = layoutCoordinates.positionInRoot().y / density.density
            }
                .alpha(
                    alpha = if (hide) 0.0f else 1.0f
                )
}

@Stable
fun Modifier.onboardingItemOffset(map: Map<Onboarding, Float>, key: Onboarding): Modifier =
    this then offset(
        y = (map[key] ?: 0f).dp
    )