package com.evothings.mhand.presentation.utils.sdkutil

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/*
TODO Rewrite using FusedLocationProvider for google-services-based devices
 */
class UserLocationManager(private val context: Context) {

    private val locationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val _cityName = MutableStateFlow("")
    val cityName = _cityName.asStateFlow()

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            tryGeocodeCityName(location.latitude, location.longitude).fold(
                onSuccess = { city ->
                    _cityName.update { city }
                },
                onFailure = { }
            )
        }

        override fun onFlushComplete(requestCode: Int) {}
        @Deprecated("Deprecated in superclass")
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onLocationChanged(locations: MutableList<Location>) {}
        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
    }

    @Suppress("MissingPermission")
    fun getLocationAndGeocode() {
        if (locationPermissionIsGranted()) {
            val providerName = getProviderName()
            val lastKnownLocation = locationManager.getLastKnownLocation(providerName)

            if (lastKnownLocation != null)
                tryGeocodeCityName(lastKnownLocation.latitude, lastKnownLocation.longitude).fold(
                    onSuccess = { city ->
                        _cityName.update { city }
                    },
                    onFailure = {}
                )
             else
                locationManager.requestLocationUpdates(
                    providerName,
                    100L,
                    0f,
                    locationListener
                )

        }
    }

    private fun getProviderName(): String =
        if (Build.MANUFACTURER == "Huawei" || Build.VERSION.SDK_INT < Build.VERSION_CODES.S)
            if (Connectivity.isMobileNetworkEnabled(context))
                LocationManager.NETWORK_PROVIDER
            else
                LocationManager.GPS_PROVIDER
        else
            LocationManager.FUSED_PROVIDER

    fun disableLocationListener() = locationManager.removeUpdates(locationListener)

    private fun locationPermissionIsGranted(): Boolean =
        context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun tryGeocodeCityName(lat: Double, lon: Double): Result<String> =
        runCatching {
            geocodeCityName(lat, lon)
        }

    @Suppress("deprecation")
    private fun geocodeCityName(lat: Double, lon: Double): String {
        val geocoder = Geocoder(context)
        var cityName = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val geocodeListener = Geocoder.GeocodeListener { addresses ->
                cityName = addresses[0].locality ?: ""
            }
            geocoder.getFromLocation(lat, lon, 1, geocodeListener)
        } else {
            val geocodedAddress = geocoder.getFromLocation(lat, lon, 1)
            cityName = geocodedAddress?.get(0)?.locality ?: ""
        }
        return cityName
    }

}