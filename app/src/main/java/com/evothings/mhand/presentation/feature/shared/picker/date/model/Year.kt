package com.evothings.mhand.presentation.feature.shared.picker.date.model

import androidx.compose.runtime.Immutable

@Immutable
data class Year(
    val value: Int,
    val isSelected: Boolean
)
