package com.evothings.mhand.presentation.feature.shops.ui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.shops.model.Shop
import com.evothings.domain.util.Mock
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.mock.MockNavigationBar
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.screen.ServerErrorScreen
import com.evothings.mhand.presentation.feature.shops.ui.components.ShopsMap
import com.evothings.mhand.presentation.feature.shops.ui.components.ShopsMapOverlay
import com.evothings.mhand.presentation.feature.shops.viewmodel.ShopsContract
import com.evothings.mhand.presentation.feature.shops.viewmodel.ShopsViewModel
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.utils.maps.findNearestPointIndex
import com.evothings.mhand.presentation.utils.maps.toGeoPoint
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

private interface ShopsCallback {
    fun dialToShop(phone: String) {}
    fun refresh() {}
}

@Stable
private object EmptyShopsCallback : ShopsCallback

@Composable
fun ShopsScreen(vm: ShopsViewModel) {

    val context = LocalContext.current

    val state by vm.state.collectAsStateWithLifecycle()
    val shopsList by vm.shopsList.collectAsState()

    LaunchedEffect(vm.effect) {
        vm.effect.collect {
            when(it) {
                is ShopsContract.Effect.ShowErrorToast ->
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(state) {
        if (state is ShopsContract.State.Loading) {
            vm.handleEvent(ShopsContract.Event.LoadData)
        }
    }

    val callback = object : ShopsCallback {

        override fun dialToShop(phone: String) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            context.startActivity(intent)
        }

        override fun refresh() =
            vm.handleEvent(ShopsContract.Event.Refresh)

    }

    Surface(color = colorScheme.background) {
        when(state) {
            is ShopsContract.State.Loading -> {
                LoadingScreen()
            }
            is ShopsContract.State.Loaded -> {
                Content(
                    shops = shopsList,
                    callback = callback
                )
            }
            is ShopsContract.State.ServerError -> {
                ServerErrorScreen(
                    onRefresh = callback::refresh
                )
            }
        }
    }


}


@Composable
private fun Content(
    shops: ImmutableList<Shop>,
    callback: ShopsCallback = EmptyShopsCallback
) {

    val shopPoints = remember(shops) {
        shops.map { it.point.toGeoPoint() }
    }

    var currentItemIndex by remember { mutableIntStateOf(0) }

    val currentShopPoint = remember(currentItemIndex) {
        shops[currentItemIndex].point.toGeoPoint()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        ShopsMap(
            modifier = Modifier.fillMaxSize(),
            shopPoints = shopPoints,
            selectedShopPoint = currentShopPoint,
            onShopClick = { point ->
                val nearestShopIndex = shopPoints.findNearestPointIndex(point)
                currentItemIndex = nearestShopIndex
            }
        )

        ShopsMapOverlay(
            shops = shops,
            currentShopIndex = currentItemIndex,
            onSelect = { currentItemIndex = it },
            onClickDial = callback::dialToShop
        )

    }
}




@Preview(showSystemUi = true, fontScale = 1.65f)
@Preview(showSystemUi = true)
@Composable
private fun ShopsScreenUpscaledPreview() {
    MegahandTheme {
        Scaffold(
            bottomBar = { MockNavigationBar(NavGraph.BottomNav.Card) }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Content(
                    shops = Mock.demoShopsList.toImmutableList()
                )
            }
        }
    }
}