package com.evothings.mhand.presentation.feature.shared.picker.date.model

import androidx.compose.runtime.Immutable

@Immutable
data class Day(
    val number: Int,
    val isActive: Boolean,
    val isSelected: Boolean
)
