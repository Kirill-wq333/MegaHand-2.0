package com.evothings.mhand.presentation.feature.address.ui.screen.overlay

import org.osmdroid.util.GeoPoint

fun interface PointTapCallback {
    fun onTap(point: GeoPoint)
}