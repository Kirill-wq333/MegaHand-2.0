package com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.checkCity

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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.evothings.mhand.presentation.utils.sdkutil.UserLocationManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Deprecated(message = "Probably won't be used at all")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckCityLayout(
    vm: CheckCityViewModel = hiltViewModel(),
    openChooseCityScreen: () -> Unit,
    content: @Composable () -> Unit
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val locationManager = UserLocationManager(context)

    val checkCityBottomSheetEnabled by vm.bottomSheetEnabled.collectAsState()

    val checkCityBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

//    val locationPermissionState = rememberPermissionState(
//        permission = Manifest.permission.ACCESS_FINE_LOCATION
//    )

    LaunchedEffect(locationManager.cityName) {
        locationManager.cityName.collectLatest { cityName ->
            if (cityName != "") {
                vm.transliterateAndSaveCity(cityName)
                locationManager.disableLocationListener()
            }
        }
    }

//    LaunchedEffect(Unit) {
//        if (locationPermissionState.status.isGranted)
//            locationManager.getLocationAndGeocode()
//        else
//            locationPermissionState.launchPermissionRequest()
//    }

    content.invoke()

//    val chooseCityResult = navController.currentBackStackEntry
//        ?.savedStateHandle
//        ?.getLiveData<String>("chooseCityBundle")
//        ?.observeAsState()
//
//    chooseCityResult?.value?.let { state ->
//        vm.saveCity(state.fromJson<SavedProfileState>().city)
//        navController.currentBackStackEntry?.savedStateHandle?.remove<String>("chooseCityBundle")
//    }

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
                city = "",
                onConfirm = { hideBottomSheet() },
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
            text = city,
            style = typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.confirm_city_title),
            style = typography.labelMedium.copy(colorScheme.secondary.copy(0.4f))
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledButton(
                modifier = Modifier
                    .fillMaxWidth(0.4775f)
                    .height(45.dp),
                label = stringResource(R.string.confirm_city_correct),
                onClick = onConfirm,
            )
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                label = stringResource(R.string.confirm_city_choose_another),
                onClick = onClickChooseAnother
            )
        }
    }

}