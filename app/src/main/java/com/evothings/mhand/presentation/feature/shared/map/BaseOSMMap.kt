package com.evothings.mhand.presentation.feature.shared.map

import android.content.Context
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView

@Composable
fun BaseOSMMap(
    modifier: Modifier,
    mapViewFactoryCallback: (MapView) -> Unit,
    updateCallback: (MapView) -> Unit
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    var map: MapView? = null

    AndroidView(
        modifier = modifier,
        factory = { context ->
            Configuration.getInstance()
                .load(context, context.getSharedPreferences("maps", Context.MODE_PRIVATE))
            val mapView = MapView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
            }
            mapView.setMultiTouchControls(true)
            map = mapView
            mapViewFactoryCallback(mapView)
            mapView
        },
        update = updateCallback
    )

    DisposableEffect(map) {
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_PAUSE -> map?.onPause()
                Lifecycle.Event.ON_RESUME -> map?.onResume()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

}