package com.evothings.mhand.presentation.feature.shared.map


import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.evothings.mhand.presentation.utils.maps.getScaledMarkerDrawable
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@Composable
fun PlacemarkMap(
    modifier: Modifier,
    currentPoint: GeoPoint,
    points: List<GeoPoint>,
    initialZoom: Double = 16.0,
    @DrawableRes iconRes: Int,
    iconScale: Float,
    flyToCurrent: Boolean = true,
    onPointClick: (GeoPoint) -> Unit
) {

    val resources = LocalContext.current.resources

    val markerIcon = remember {
        getScaledMarkerDrawable(resources, iconRes, iconScale)
    }

    val markerTapListener = Marker.OnMarkerClickListener { marker, mapView ->
        val position = marker.position
        mapView.controller.animateTo(position)
        onPointClick(position)
        true
    }

    BaseOSMMap(
        modifier = modifier,
        mapViewFactoryCallback = { mapView ->
            mapView.controller.apply {
                setZoom(initialZoom)
                setCenter(currentPoint)
            }
        },
        updateCallback = { view ->
            if (flyToCurrent) {
                view.controller.apply {
                    animateTo(currentPoint)
                }
            }

            val markers = points.map { point ->
                Marker(view).apply {
                    position = point
                    icon = markerIcon
                    setOnMarkerClickListener(markerTapListener)
                }
            }

            view.overlays.addAll(markers)
        }
    )

}