package com.evothings.mhand.presentation.feature.splash.viewmodel.checkUpdate

interface CheckUpdateService {
    fun checkIsUpdateAvailable(callback: (Boolean) -> Unit)
}