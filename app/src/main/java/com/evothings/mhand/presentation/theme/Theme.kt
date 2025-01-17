package com.evothings.mhand.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import com.evothings.mhand.presentation.theme.colorScheme.DarkColorScheme
import com.evothings.mhand.presentation.theme.colorScheme.LightColorScheme
import com.evothings.mhand.presentation.theme.values.MegahandShapes
import com.evothings.mhand.presentation.theme.values.paddings.LocalPaddings
import com.evothings.mhand.presentation.theme.values.spacers.LocalSpacers

val MaterialTheme.paddings
    @Composable
    get() = LocalPaddings.current

val MaterialTheme.spacers
    @Composable
    get() = LocalSpacers.current

@Composable
fun MegahandTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.onTertiary.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = MegahandShapes,
        typography = MegahandTypography,
        content = content
    )

}