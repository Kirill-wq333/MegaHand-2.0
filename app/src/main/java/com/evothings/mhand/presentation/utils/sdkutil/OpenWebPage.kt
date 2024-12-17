package com.evothings.mhand.presentation.utils.sdkutil

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.evothings.mhand.R

fun openPrivacyPolicyPage(context: Context) =
    tryOpenWebPage(context, "https://mhand.ru/static/file/privacy-policy.pdf")

fun tryOpenWebPage(context: Context, url: String) =
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch(e: ActivityNotFoundException) {
        Toast.makeText(context, context.getString(R.string.unable_to_open_link), Toast.LENGTH_SHORT).show()
    }