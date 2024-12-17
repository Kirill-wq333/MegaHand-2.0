package com.evothings.mhand.presentation.utils.analytics

import com.my.tracker.MyTracker

fun trackAnalyticsEvent(
    name: String,
    eventParams: Map<String, String> = mapOf()
) = MyTracker.trackEvent(name, eventParams)