package com.evothings.widget.components.modifier

import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.background
import androidx.glance.layout.fillMaxSize
import com.evothings.widget.R

fun GlanceModifier.widgetLayout(): GlanceModifier =
    this then GlanceModifier
        .fillMaxSize()
        .background(imageProvider = ImageProvider(R.drawable.widget_background))
        .appWidgetBackground()