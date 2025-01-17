package com.evothings.mhand.presentation.feature.payment

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evothings.mhand.R

class PaymentActivity : AppCompatActivity() {

    companion object {
        const val PAYMENT_LINK_EXTRA = "paymentLink"
    }

    private val paymentSuccessLink = "mhand://success-order"
    private val paymentFailureLink = "mhand://order-failure"

    private val webViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            if (url == paymentSuccessLink) {
                setResult(Activity.RESULT_OK)
                finish()
            } else if (url == paymentFailureLink) {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent?.extras == null || intent?.extras?.containsKey(PAYMENT_LINK_EXTRA) == false) {
            Toast.makeText(this, getString(R.string.payment_link_receive_failure), Toast.LENGTH_SHORT).show()
            finish()
        }
        setContentView(R.layout.payment_activity)

        val paymentUrl = intent?.extras?.getString(PAYMENT_LINK_EXTRA) ?: ""
        val webView = findViewById<WebView>(R.id.payment_web_view)
        webView.webViewClient = webViewClient
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(paymentUrl)
    }

}