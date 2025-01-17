package com.evothings.mhand.presentation.feature.address.ui.screen.overlay

import android.graphics.drawable.Drawable
import android.view.MotionEvent
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Overlay

class MapTapOverlay(
    private val markerDrawable: Drawable?,
    private val pointTapCallback: PointTapCallback
) : Overlay() {

    private var markerIndex: Int = -1

    override fun onSingleTapConfirmed(e: MotionEvent, mapView: MapView): Boolean {
        val geoPoint = mapView.projection.fromPixels(e.x.toInt(), e.y.toInt()) as GeoPoint
        placeMarker(mapView, geoPoint)
        pointTapCallback.onTap(geoPoint)
        return true
    }

    private fun placeMarker(map: MapView, point: GeoPoint) {
        val marker = Marker(map).apply {
            position = point
            icon = markerDrawable
            infoWindow = null
            setOnMarkerClickListener { _, _ -> true }
        }
        if (markerIndex > 0) {
            map.overlays.removeAt(markerIndex)
        }
        map.overlays.add(marker)
        markerIndex = map.overlays.lastIndex
        map.invalidate()
    }

}