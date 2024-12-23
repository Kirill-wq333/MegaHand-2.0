package com.evothings.mhand.presentation.feature.snackbar.host

import com.evothings.mhand.presentation.feature.snackbar.model.SnackbarItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SnackbarItemHost {

    private val _currentSnackbarItem: MutableStateFlow<SnackbarItem?> = MutableStateFlow(null)
    val currentSnackbarItem = _currentSnackbarItem.asStateFlow()

    fun setSnackbar(snackbarItem: SnackbarItem?) {
        _currentSnackbarItem.update { snackbarItem }
    }

}