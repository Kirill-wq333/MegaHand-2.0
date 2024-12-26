package com.evothings.mhand.presentation.feature.shared.screen.chooseCity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.home.model.CitiesMap
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.screen.NoInternetConnectionScreen
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.components.TextFieldAndTitle
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.components.TitleCity
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.viewmodel.ChooseCityContract
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.viewmodel.ChooseCityViewModel
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import kotlinx.coroutines.launch

private data class ChooseCityUiState(
    val letterListState: LazyListState,
    val citiesListState: LazyListState,
    val citiesMap: CitiesMap,
    val chosenLetter: String
)

private interface ChooseCityCallback {
    fun searchCities(query: String) {}
    fun loadCities() {}
    fun onChooseLetter(index: Int, letter: String) {}
    fun onChooseCity(city: String) {}
}

@Stable
internal object EmptyChooseCityCallback : ChooseCityCallback

@Composable
fun ChooseCityScreen(
    modifier: Modifier = Modifier,
    vm: ChooseCityViewModel = hiltViewModel(),
    markChosen: Boolean = true,
    onChoose: (String) -> Unit,
) {
    val state by vm.state.collectAsStateWithLifecycle()

    val citiesMap by vm.citiesMap.collectAsState()

    LaunchedEffect(Unit) {
        vm.handleEvent(ChooseCityContract.Event.LoadCities(markChosen))
    }

    val citiesListState = rememberLazyListState()
    val letterRowState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    val chosenLetter = remember { mutableStateOf("А") }

    val callback = object : ChooseCityCallback {
        override fun loadCities() =
            vm.handleEvent(ChooseCityContract.Event.LoadCities(markChosen))

        override fun onChooseCity(city: String) {
            vm.handleEvent(ChooseCityContract.Event.ChooseCity(city))
            onChoose(city)
        }

        override fun searchCities(query: String) =
            vm.handleEvent(ChooseCityContract.Event.SearchCity(query))

        override fun onChooseLetter(index: Int, letter: String) {
            chosenLetter.value = letter
            scope.launch { citiesListState.scrollToItem(index) }
        }
    }

    Content(
        modifier = modifier,
        state = state,
        uiState = ChooseCityUiState(
            letterListState = letterRowState,
            citiesListState = citiesListState,
            citiesMap = citiesMap,
            chosenLetter = chosenLetter.value
        ),
        callback = callback
    )
}

@Composable
private fun Content(
    modifier: Modifier,
    state: ChooseCityContract.State,
    uiState: ChooseCityUiState,
    callback: ChooseCityCallback = EmptyChooseCityCallback,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextFieldAndTitle()
        when(state) {
            is ChooseCityContract.State.Loading -> LoadingScreen()
            is ChooseCityContract.State.Loaded -> {
                CitiesMap(
                    uiState = uiState,
                    callback = callback
                )
            }
            is ChooseCityContract.State.NetworkError -> {
                NoInternetConnectionScreen(
                    onReload = callback::loadCities
                )
            }
        }
    }
}

@Composable
private fun CitiesMap(
    modifier: Modifier = Modifier,
    uiState: ChooseCityUiState,
    callback: ChooseCityCallback
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                vertical = MaterialTheme.paddings.extraGiant,
                horizontal = MaterialTheme.paddings.extraLarge
            ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.mega),
        state = uiState.citiesListState
    ) {
        items(uiState.citiesMap.keys.toList()) { item ->
            TitleCity(
                title = item,
                cities = uiState.citiesMap[item]!!,
                onChoose = callback::onChooseCity
            )
        }
    }
}

