package com.evothings.widget.viewmodel.uiState

import com.evothings.widget.viewmodel.WidgetState

data class MhandWidgetUiState(
    val state: WidgetState,
    val cardBalance: Int,
    val qrLink: String?
)
