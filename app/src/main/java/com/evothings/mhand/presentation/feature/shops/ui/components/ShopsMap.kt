package com.evothings.mhand.presentation.feature.shops.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.map.PlacemarkMap
import org.osmdroid.util.GeoPoint

@Composable
fun ShopsMap(
    modifier: Modifier,
    shopPoints: List<GeoPoint>,
    selectedShopPoint: GeoPoint,
    onShopClick: (GeoPoint) -> Unit
) {

    PlacemarkMap(
        modifier = modifier,
        currentPoint = selectedShopPoint,
        points = shopPoints,
        initialZoom = 15.0,
        iconRes = R.drawable.shop_map_point,
        iconScale = 0.25f,
        onPointClick = onShopClick
    )

}