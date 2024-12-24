package com.evothings.mhand.presentation.feature.shared.screen.chooseCity

import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.checkout.model.MapPoint
import com.evothings.domain.feature.home.model.City
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.address.viewmodel.map.AddressMapContract
import com.evothings.mhand.presentation.feature.address.viewmodel.map.AddressMapViewModel
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.BottomBarNavigation
import com.evothings.mhand.presentation.feature.shared.header.Header
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.components.TextFieldAndTitle
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.components.TitleCity
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.viewmodel.ChooseCityViewModel
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import java.util.Locale

@Composable
fun ChooseCityScreen(
) {
    Content(
    )
}

@Composable
private fun Content(

) {

    val city = listOf(
        Title("А",),
        Title("А",),
        Title("А",),
        Title("А",),
        Title("А",),
        Title("А",),
        Title("А",),
        Title("А",),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextFieldAndTitle()
        LazyColumn(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.paddings.extraGiant,
                    horizontal = MaterialTheme.paddings.extraLarge
                ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.mega)
        ) {
            items(city) { item ->
                TitleCity(
                    title = item.title,
                )
            }
        }
    }
}

data class Title(
    val title: String,
)
