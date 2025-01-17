package com.evothings.mhand.presentation.feature.address.ui.screen

import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.checkout.model.MapPoint
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.address.ui.screen.overlay.MapTapOverlay
import com.evothings.mhand.presentation.feature.address.ui.screen.overlay.PointTapCallback
import com.evothings.mhand.presentation.feature.address.viewmodel.map.AddressMapContract
import com.evothings.mhand.presentation.feature.address.viewmodel.map.AddressMapViewModel
import com.evothings.mhand.presentation.feature.address.viewmodel.model.AddressMapResult
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.header.ui.HeaderProvider
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.map.BaseOSMMap
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.maps.getScaledMarkerDrawable
import com.evothings.mhand.presentation.utils.maps.toGeoPoint
import com.evothings.mhand.presentation.utils.maps.toMapPoint
import java.util.Locale

@Suppress("Deprecation")
@Composable
fun AddressMapScreen(
    vm: AddressMapViewModel = hiltViewModel(),
    city: String,
    onSelect: (AddressMapResult) -> Unit,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val geocoder = remember { Geocoder(context, Locale.forLanguageTag("ru")) }

    val state by vm.state.collectAsStateWithLifecycle()

    val initialPoint by vm.initialPoint.collectAsState()
    val address by vm.address.collectAsState()

    LaunchedEffect(Unit) {
        vm.handleEvent(
            if (city.isNotEmpty()) {
                AddressMapContract.Event.SetCity(city)
            } else {
                AddressMapContract.Event.SetUserDefaultCity
            }
        )
    }

    val resolvedCity by vm.city.collectAsState()
    LaunchedEffect(resolvedCity) {
        if (resolvedCity.isEmpty()) return@LaunchedEffect
        val guessedCityCoordinates =
            geocoder.getFromLocationName(resolvedCity, 1)?.firstOrNull()
                ?: return@LaunchedEffect

        vm.handleEvent(
            AddressMapContract.Event.SetInitialPoint(
                MapPoint(
                    latitude = guessedCityCoordinates.latitude,
                    longitude = guessedCityCoordinates.longitude
                )
            )
        )
    }

    var pointToGeocode by remember { mutableStateOf(MapPoint(0.0, 0.0)) }

    LaunchedEffect(pointToGeocode) {
        if (pointToGeocode.latitude > 0.0 && pointToGeocode.longitude > 0.0) {
            val guessedAddress =
                geocoder.getFromLocation(
                    pointToGeocode.latitude,
                    pointToGeocode.longitude,
                    1
                )?.firstOrNull() ?: return@LaunchedEffect

            vm.handleEvent(
                AddressMapContract.Event.ConsumeGuessedAddress(guessedAddress)
            )
        }
    }

    AddressMapContent(
        state = state,
        initialPoint = initialPoint,
        address = address,
        onSave = onSelect,
        convertToAddress = { pointToGeocode = it },
        onBack = onBack
    )

}

@Composable
private fun AddressMapContent(
    state: AddressMapContract.State,
    initialPoint: MapPoint,
    address: String,
    onSave: (AddressMapResult) -> Unit,
    convertToAddress: (MapPoint) -> Unit,
    onBack: () -> Unit,
) {

    val parsedAddress: AddressMapResult = remember(address) {
        val segments = address.trim().split(',')
        val trimmed = segments.map { it.trim() }

        AddressMapResult(
            postalCode = trimmed.getOrNull(0).orEmpty(),
            city = trimmed.getOrNull(1).orEmpty(),
            street = trimmed.getOrNull(2).orEmpty(),
            house = trimmed.getOrNull(3).orEmpty()
        )
    }

    HeaderProvider(
        screenTitle = stringResource(R.string.address_map_heading),
        enableBackButton = true,
        onBack = onBack,
        enableMapIconButton = false,
        enableNotificationButton = false,
        enableCardBalance = false
    ) { headerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(headerPadding)
        ) {
            when(state) {
                is AddressMapContract.State.InitialPointLoading -> LoadingScreen()
                is AddressMapContract.State.Loaded -> {
                    Content(
                        initialPoint = initialPoint,
                        address = address,
                        convertToAddress = convertToAddress,
                        onSave = { onSave(parsedAddress); onBack() },
                        onBack = onBack
                    )
                }
            }
        }

    }

}

@Composable
private fun BoxScope.Content(
    initialPoint: MapPoint,
    address: String,
    convertToAddress: (MapPoint) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit
) {

    var currentPoint by remember { mutableStateOf(initialPoint) }

    Map(
        point = currentPoint,
        onChoose = { point ->
            currentPoint = point
            convertToAddress(point)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) {
        AddressCard(
            address = address
        )
        Actions(
            enableSaveButton = (address.isNotEmpty() && !address.contains("null")),
            onCancel = onBack,
            onSave = onSave
        )
    }

}

@Composable
private fun Map(
    point: MapPoint,
    onChoose: (MapPoint) -> Unit
) {

    val resources = LocalContext.current.resources

    val pointDrawableIcon = remember {
        getScaledMarkerDrawable(
            resources = resources,
            iconRes = R.drawable.location_pin,
            scale = 0.08f
        )
    }

    val mapTapCallback = remember {
        PointTapCallback { tappedPoint ->
            val mapPoint = tappedPoint.toMapPoint()
            onChoose(mapPoint)
        }
    }
    val tapOverlay = remember { MapTapOverlay(pointDrawableIcon, mapTapCallback) }

    val geoPoint = remember(point) { point.toGeoPoint() }

    BaseOSMMap(
        modifier = Modifier.fillMaxSize(),
        mapViewFactoryCallback = { map ->
            map.controller.apply {
                setZoom(16.0)
                setCenter(geoPoint)
            }
            map.overlays.add(tapOverlay)
        },
        updateCallback = {}
    )

//    val inputListener = object : InputListener {
//
//        override fun onMapTap(p0: Map, p1: Point) {
//            val geoPoint = p1.toGeoPoint()
//            onChoose(geoPoint)
//        }
//
//        override fun onMapLongTap(p0: Map, p1: Point) {}
//    }
//
//    AndroidView(
//        factory = { context ->
//            val mapView = MapView(context).apply {
//                layoutParams = ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                )
//            }
//            MapKitFactory.initialize(context)
//
//            val movePosition = CameraPosition(yaMapsPoint, 16f, 0f, 0f)
//            mapView.mapWindow.map.move(movePosition)
//
//            mapView
//        },
//        update = { mapView ->
//
//            mapView.mapWindow.map.apply {
//                mapObjects.clear()
//                mapObjects.addPlacemark().apply {
//                    geometry = yaMapsPoint
//                    setCompositeIcon(mapView.context, R.drawable.location_pin, 0.08f)
//                }
//                addInputListener(inputListener)
//            }
//
//        }
//    )

}

@Composable
private fun AddressCard(
    address: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = MaterialTheme.spacers.medium)
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = MaterialTheme.shapes.extraLarge
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = address,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(all = MaterialTheme.spacers.extraLarge)
        )
    }
}

@Composable
private fun Actions(
    enableSaveButton: Boolean,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacers.large)
        ) {
            Button(
                modifier = Modifier.weight(0.5f),
                text = stringResource(id = R.string.cancel),
                backgroundColor = Color.Transparent,
                borderColor = MaterialTheme.colorScheme.secondary.copy(0.1f),
                onClick = onCancel
            )
            Spacer(
                modifier = Modifier
                    .width(MaterialTheme.spacers.medium)
            )
            Button(
                modifier = Modifier.weight(0.5f),
                isEnabled = enableSaveButton,
                text = stringResource(id = R.string.proceed),
                backgroundColor = ColorTokens.Sunflower,
                textColor = ColorTokens.Graphite,
                onClick = onSave
            )
        }

    }
}