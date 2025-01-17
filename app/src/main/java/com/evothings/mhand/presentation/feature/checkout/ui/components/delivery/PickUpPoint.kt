package com.evothings.mhand.presentation.feature.checkout.ui.components.delivery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.checkout.model.PickupPoint
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.chooseCity.ChooseCityModal
import com.evothings.mhand.presentation.feature.shared.map.PlacemarkMap
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.feature.shared.text.TrailingButtonTextField
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.maps.findNearestPointIndex
import com.evothings.mhand.presentation.utils.maps.toGeoPoint

@Composable
fun PickUpPoint(
    points: List<PickupPoint>,
    city: String,
    selectedPoint: PickupPoint,
    onChangeCity: (String) -> Unit,
    onSelectPoint: (PickupPoint) -> Unit,
) {
    var cityBottomSheetVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TrailingButtonTextField(
            value = city,
            label = stringResource(id = R.string.city),
            buttonLabel = stringResource(id = R.string.choose),
            onValueChange = onChangeCity,
            onClickTrailingButton = { cityBottomSheetVisible = true }
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )

        if (points.isNotEmpty()) {
            AvailablePoints(
                points = points,
                selectedPoint = selectedPoint,
                onSelectPoint = onSelectPoint
            )
        } else {
            PointsListEmpty()
        }

    }

    if (cityBottomSheetVisible) {
        MhandModalBottomSheet(
            onDismissRequest = { cityBottomSheetVisible = false }
        ) { hide ->
            ChooseCityModal(
                modifier = Modifier.modalBottomSheetPadding(),
                onDismiss = hide,
                onChoose = { onChangeCity(it); hide() }
            )
        }
    }

}

@Composable
private fun AvailablePoints(
    points: List<PickupPoint>,
    selectedPoint: PickupPoint,
    onSelectPoint: (PickupPoint) -> Unit
) {
    Text(
        text = selectedPoint.address,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(
        modifier = Modifier
            .height(MaterialTheme.spacers.large)
    )
    PointsMap(
        points = points,
        currentPoint = selectedPoint,
        onSelect = onSelectPoint,
    )
    Spacer(
        modifier = Modifier
            .height(MaterialTheme.spacers.large)
    )
    Text(
        text = stringResource(R.string.pickup_point_notice),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.secondary.copy(0.6f)
    )
}

@Composable
private fun PointsListEmpty() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.pickup_points_list_empty_warning),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun PointsMap(
    points: List<PickupPoint>,
    currentPoint: PickupPoint,
    onSelect: (PickupPoint) -> Unit,
) {

    val mappedPoints = remember(points) {
        points.map { it.point.toGeoPoint() }
    }

    val pickupAsPoint = remember(currentPoint) { currentPoint.point.toGeoPoint() }

    PlacemarkMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .clip(MaterialTheme.shapes.large),
        currentPoint = pickupAsPoint,
        points = mappedPoints,
        iconRes = R.drawable.cdek_map_point,
        initialZoom = 14.0,
        iconScale = 0.25f,
        onPointClick = { point ->
            val itemIndex = mappedPoints.findNearestPointIndex(point)
            val item = points[itemIndex]
            onSelect(item)
        }
    )

}