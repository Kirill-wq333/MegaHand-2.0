package com.evothings.mhand.presentation.feature.shared.modifier

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.modalBottomSheetPadding(): Modifier = this then padding(
    start = 24.dp,
    end = 24.dp,
    bottom = 24.dp
)