package com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.checkCity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.FilledButton
import com.evothings.mhand.presentation.feature.shared.button.OutlinedButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.utils.sdkutil.UserLocationManager
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "location_prefs")
val LOCATION_KEY = stringPreferencesKey("location")

@SuppressLint("MissingPermission")
@Deprecated(message = "Probably won't be used at all")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckCityLayout(
    vm: CheckCityViewModel = hiltViewModel(),
    openChooseCityScreen: () -> Unit,
    content: @Composable () -> Unit
) {

    val context = LocalContext.current
    val locationManager = UserLocationManager(context)
    val scope = rememberCoroutineScope()
    var locationName by remember { mutableStateOf("Неизвестно") }
    var hasLocationPermission by remember {
        mutableStateOf(
            hasLocationPermission(context)
        )
    }

    val checkCityBottomSheetEnabled by vm.bottomSheetEnabled.collectAsState()
    val locationClient = LocationServices.getFusedLocationProviderClient(context)

    val checkCityBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
    }

    val fetchLocation = {
        if (hasLocationPermission) {
            locationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    scope.launch {
                        val cityName = getCityName(location, context)
                        locationName = cityName
                        saveLocation(cityName, context)
                    }
                }
            }
        } else {
            // если разрешения нет, то запрашиваем
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            } else {

            }
        }
    }


    LaunchedEffect(Unit) {
        if (hasLocationPermission) {
            fetchLocation()
            loadLocation(context, { locationName = it })
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

    LaunchedEffect(locationManager.cityName) {
        locationManager.cityName.collectLatest { cityName ->
            if (cityName != "") {
                vm.transliterateAndSaveCity(cityName)
                locationManager.disableLocationListener()
            }
        }
    }

    content.invoke()

    fun hideBottomSheet() {
        scope.launch {
            checkCityBottomSheetState.hide()
            vm.disableBottomSheet()
        }
    }

    if (checkCityBottomSheetEnabled)
        ModalBottomSheet(
            sheetState = checkCityBottomSheetState,
            onDismissRequest = vm::disableBottomSheet,
            containerColor = colorScheme.surface,
            shape = RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp
            )
        ) {
            CheckCityBottomSheet(
                city = locationName,
                onConfirm = { fetchLocation() },
                onClickChooseAnother = {
                    hideBottomSheet()
                    openChooseCityScreen()
                }
            )
        }
}

@Composable
fun CheckCityBottomSheet(
    city: String,
    onConfirm: () -> Unit,
    onClickChooseAnother: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 15.dp,
                bottom = 20.dp
            )
            .offset(y = (-10).dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.confirm_city_title, city),
            style = typography.labelMedium.copy(colorScheme.secondary)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledButton(
                modifier = Modifier
                    .weight(.5f)
                    .height(44.dp),
                label = stringResource(R.string.confirm_city_correct),
                onClick = onConfirm,
            )
            OutlinedButton(
                modifier = Modifier
                    .weight(.5f)
                    .height(44.dp),
                label = stringResource(R.string.confirm_city_choose_another),
                onClick = onClickChooseAnother
            )
        }
    }

}


private suspend fun loadLocation(
    context: Context,
    onLocationLoaded: (String) -> Unit
) {
    context.dataStore.data.collect { preferences ->
        val savedLocation = preferences[LOCATION_KEY] ?: "Неизвестно"
        onLocationLoaded(savedLocation)
    }
}

private suspend fun saveLocation(cityName: String, context: Context) {
    context.dataStore.edit { preferences ->
        preferences[LOCATION_KEY] = cityName
    }
}

private fun hasLocationPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

private fun getCityName(location: Location, context: Context): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

    return if (addresses != null && addresses.isNotEmpty()) {
        val address = addresses[0]
        address.locality ?: "Неизвестно"
    } else {
        "Неизвестно"
    }
}

@Preview
@Composable
private fun Preview() {
    MegahandTheme {
        CheckCityLayout(
            openChooseCityScreen = {},
            content = {}
        )
    }
}