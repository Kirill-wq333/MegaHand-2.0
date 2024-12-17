package com.evothings.widget.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.evothings.domain.feature.card.model.Card
import com.evothings.domain.feature.auth.repository.AuthRepository
import com.evothings.domain.feature.card.repository.CardRepository
import com.evothings.widget.viewmodel.uiState.MhandWidgetUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MhandWidgetDataLoader(
    private val cardRepository: CardRepository,
    private val authRepository: AuthRepository
) {

    private val _uiState: MutableStateFlow<MhandWidgetUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<MhandWidgetUiState?>
        get() = _uiState.asStateFlow()

    private val defaultCard: Card =
        Card(
            balance = 0,
            barcodeUrl = null
        )

    suspend fun updateWidgetUiState() = withContext(Dispatchers.IO) {
        val cardInfo = cardRepository.fetchCardOffline().getOrDefault(defaultCard)
        val state = getState()
        _uiState.emit(
            MhandWidgetUiState(
                state = state,
                cardBalance = cardInfo.balance,
                qrLink = cardInfo.barcodeUrl
            )
        )
    }

    private suspend fun getState(): WidgetState {
        val isAuthorized = authRepository.isLoggedIn()
        if (!isAuthorized) return WidgetState.NotAuthorized
        return cardRepository.fetchCardOffline()
            .fold(
                onSuccess = { WidgetState.Success },
                onFailure = { WidgetState.LoyalityNotAvailable }
            )
    }

    suspend fun getOrDownloadBitmap(link: String, directory: File): Bitmap? {
        val bitmapFile = File("$directory/${link.hashCode()}.png")
        if (!bitmapFile.exists()) {
            downloadBitmap(link, bitmapFile)
        }
        return runCatching {
            BitmapFactory.decodeFile(bitmapFile.path)
        }.getOrNull()
    }

    private suspend fun downloadBitmap(link: String, into: File) = withContext(Dispatchers.IO) {
        try {
            val url = URL(link)
            val connection = url.openConnection() as HttpsURLConnection
            connection.connect()
            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                FileOutputStream(into).use {
                    BufferedOutputStream(it).use { os ->
                        inputStream.copyTo(os)
                    }
                }
            } else {
                Log.e("MhandWidgetDataLoader", "Bitmap Load HTTP Code: ${connection.responseCode}")
            }
        } catch(e: IOException) {
            Log.e("MhandWidgetDataLoader", "Failed to load bitmap: ${e.message}")
        }
    }

}