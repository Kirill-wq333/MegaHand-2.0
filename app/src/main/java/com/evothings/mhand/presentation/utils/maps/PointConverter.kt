package com.evothings.mhand.presentation.utils.maps

import com.evothings.domain.feature.checkout.model.MapPoint
import org.osmdroid.util.GeoPoint

fun MapPoint.toGeoPoint(): GeoPoint =
    GeoPoint(latitude, longitude)

fun GeoPoint.toMapPoint(): MapPoint =
    MapPoint(latitude, longitude)