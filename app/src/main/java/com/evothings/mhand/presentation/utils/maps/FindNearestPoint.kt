package com.evothings.mhand.presentation.utils.maps

import android.location.Location
import org.osmdroid.util.GeoPoint
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

fun List<GeoPoint>.findNearestPointIndex(clickedPosition: GeoPoint): Int {
    var curMinDistance = Float.MAX_VALUE
    var index = 0
    for (point in this) {
        val clickedLocation = clickedPosition.asLocation()
        val pointLocation = point.asLocation()
        val distance = pointLocation.distanceTo(clickedLocation)
        if (distance < curMinDistance) {
            curMinDistance = distance
            index = this.indexOf(point)
        }
    }
    return index
}

private fun GeoPoint.asLocation(): Location {
    val point = this
    val locationInstance = Location("")
    locationInstance.apply {
        this.latitude = point.latitude
        this.longitude = point.longitude
    }
    return locationInstance
}

@Deprecated("Use findNearestPointIndex")
fun calculateDistanceBetweenPoints(
    lat1: Double,
    lon1: Double,
    lat2: Double,
    lon2: Double
): Int {
    val earthRadius = 6371000.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2) * sin(dLat / 2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return (earthRadius * c).roundToInt()
}