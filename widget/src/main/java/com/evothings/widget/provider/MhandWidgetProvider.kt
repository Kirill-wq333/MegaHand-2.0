package com.evothings.widget.provider

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.evothings.widget.MhandWidget
import com.evothings.widget.viewmodel.MhandWidgetDataLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MhandWidgetProvider : GlanceAppWidgetReceiver() {

    @Inject
    lateinit var dataLoader: MhandWidgetDataLoader

    override val glanceAppWidget: GlanceAppWidget
        get() = MhandWidget(dataLoader)


}