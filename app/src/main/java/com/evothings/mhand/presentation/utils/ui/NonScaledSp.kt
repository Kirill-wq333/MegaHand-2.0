package com.evothings.mhand.presentation.utils.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp

internal val Int.nonScaledSp
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp