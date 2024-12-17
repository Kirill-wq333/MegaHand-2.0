package com.evothings.mhand.core

import android.app.Application
import com.my.tracker.MyTracker
import dagger.hilt.android.HiltAndroidApp

private const val MYTRACKER_KEY = "94455316247088064369"

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MyTracker.initTracker(MYTRACKER_KEY, this)
        MyTracker.getTrackerConfig().setBufferingPeriod(30)
    }

}