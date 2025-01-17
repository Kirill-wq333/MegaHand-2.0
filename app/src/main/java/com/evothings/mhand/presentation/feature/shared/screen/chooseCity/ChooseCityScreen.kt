package com.evothings.mhand.presentation.feature.shared.screen.chooseCity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.home.model.CitiesMap
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.text.SearchField
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.viewmodel.ChooseCityContract
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.viewmodel.ChooseCityViewModel
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.screen.NoInternetConnectionScreen
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import com.evothings.mhand.presentation.theme.MegahandTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.evothings.domain.feature.home.model.City
import com.evothings.mhand.presentation.feature.shared.button.Chip
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
private object EmptyChooseCityCallback : ChooseCityCallback

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

    var query by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SearchField(
                placeholder = stringResource(R.string.search_by_name),
                query = query,
                onValueChange = { newQuery ->
                    query = newQuery
                    callback.searchCities(query)
                },
                onClickSearch = { callback.searchCities(query) },
            )

            LazyRow(
                state = uiState.letterListState,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                itemsIndexed(
                    items = uiState.citiesMap.keys.toList(),
                    key = { _, item -> item }
                ) { index, item ->

                    val enabled = remember(uiState.chosenLetter) {
                        uiState.chosenLetter == item
                    }

                    Chip(
                        text = item,
                        enabled = enabled,
                        onClick = { callback.onChooseLetter(index, item) }
                    )

                }

            }
        }

        when(state) {
            is ChooseCityContract.State.Loading -> LoadingScreen()
            is ChooseCityContract.State.Loaded -> {
                CitiesList(
                    uiState = uiState,
                    onChoose = callback::onChooseCity
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
private fun CitiesList(
    uiState: ChooseCityUiState,
    onChoose: (String) -> Unit
) {
    LazyColumn(
        state = uiState.citiesListState,
        verticalArrangement = Arrangement.spacedBy(36.dp)
    ) {

        items(uiState.citiesMap.keys.toList()) { key ->
            LetterCitiesList(
                letter = key,
                cities = uiState.citiesMap[key]!!,
                onChoose = onChoose
            )
        }

    }
}

@Composable
private fun LetterCitiesList(
    letter: String,
    cities: List<City>,
    onChoose: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = letter,
            style = typography.headlineMedium
        )
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        repeat(cities.size) { i ->
            val city = remember(cities) { cities[i] }

            CityItem(
                name = city.name,
                isSelected = city.chosen,
                onClick = { onChoose(city.name) }
            )
        }
    }
}

@Composable
private fun CityItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val backgroundColor =
        if (isSelected) colorScheme.secondary.copy(.05f) else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = shapes.medium
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = name,
            style = typography.bodyLarge,
            modifier = Modifier.padding(12.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            thickness = 1.dp,
            color = colorScheme.secondary.copy(0.05f)
        )
    }

}

val demoCitiesMap: CitiesMap = mapOf(
    "А" to listOf(
        City(
            name = "Армавир",
            chosen = false,
            loyalityAvailable = true
        ),
        City(
            name = "Арзамас",
            chosen = false,
            loyalityAvailable = false
        ),
        City(
            name = "Архангельск",
            chosen = true,
            loyalityAvailable = true
        ),
        City(
            name = "Анапа",
            chosen = false,
            loyalityAvailable = false
        ),
        City(
            name = "Адлер",
            chosen = false,
            loyalityAvailable = false
        ),
    ),
    "Б" to listOf(
        City(
            name = "Борск",
            chosen = false,
            loyalityAvailable = true
        ),
        City(
            name = "Бобруйск",
            chosen = false,
            loyalityAvailable = false
        ),
        City(
            name = "Благовещенск",
            chosen = false,
            loyalityAvailable = false
        ),
        City(
            name = "Брест",
            chosen = false,
            loyalityAvailable = false
        ),
        City(
            name = "Борское",
            chosen = false,
            loyalityAvailable = true
        ),
    ),
    "В" to listOf(
        City(
            name = "Вильнюс",
            chosen = false,
            loyalityAvailable = true
        ),
        City(
            name = "Вологда",
            chosen = false,
            loyalityAvailable = false
        ),
        City(
            name = "Владимир",
            chosen = false,
            loyalityAvailable = true
        ),
        City(
            name = "Волгоград",
            chosen = false,
            loyalityAvailable = false
        ),
        City(
            name = "Волчанка",
            chosen = false,
            loyalityAvailable = false
        ),
    ),
)

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, fontScale = 1.65f)
@Composable
private fun ChooseCityScreenUpscaledPreview() {
    MegahandTheme {
        Surface {
            Content(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                state = ChooseCityContract.State.Loaded,
                uiState = ChooseCityUiState(
                    letterListState = rememberLazyListState(),
                    citiesListState = rememberLazyListState(),
                    citiesMap = demoCitiesMap,
                    chosenLetter = "А"
                )
            )
        }
    }
}