package com.evothings.mhand.presentation.feature.shops.ui

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Preview
@Composable
fun WebViewPagePreview() {
    WebViewPage(url = "https://www.mhand.ru/vacancy")
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(url: String) {
    val context = LocalContext.current

    val webView = remember { WebView(context).apply {
        webViewClient = WebViewClient()
        settings.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE


    }

    }

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red),
        factory = { webView },
        update = {
            it.loadUrl(url)
        })

}