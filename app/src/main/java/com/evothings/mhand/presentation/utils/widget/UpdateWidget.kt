package com.evothings.mhand.presentation.utils.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.evothings.widget.provider.MhandWidgetProvider

fun sendUpdateWidgetBroadcast(context: Context) {
    val appCtx = context.applicationContext
    val intent = Intent(appCtx, MhandWidgetProvider::class.java)
    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
    val manager = AppWidgetManager.getInstance(appCtx)
    val ids = manager.getAppWidgetIds(ComponentName(appCtx, MhandWidgetProvider::class.java))
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)

    appCtx.sendBroadcast(intent)
}