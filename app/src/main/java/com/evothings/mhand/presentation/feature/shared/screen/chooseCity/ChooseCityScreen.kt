package com.evothings.mhand.presentation.feature.shared.screen.chooseCity

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.BottomBarNavigation
import com.evothings.mhand.presentation.feature.shared.header.Header
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.components.TextFieldAndTitle
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.components.TitleCity
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun ChooseCityScreen(){
    Scaffold(
        modifier = Modifier
            .background(color = colorScheme.onSecondary),
        topBar = {
            Header(
                nameCategory = stringResource(R.string.city),
                notificationVisible = true,
                chevronLeftVisible = true,
                balanceVisible = true,
                locationVisible = true,
                money = "0",
                logoVisible = false
            )
        },
        bottomBar = { BottomBarNavigation() }
    ) {
        Box(modifier = Modifier.padding(it)){
            Content()
        }
    }
}

@Composable
private fun Content() {

    val city = listOf(
        TitleAndCity(
            "А",
            "Абакан",
        ),
        TitleAndCity(
            "А",
            "Абакан",
        ),
        TitleAndCity(
            "А",
            "Абакан",
        ),
        TitleAndCity(
            "А",
            "Абакан",
        ),
        TitleAndCity(
            "А",
            "Абакан",
        ),
        TitleAndCity(
            "А",
            "Абакан",
        ),
        TitleAndCity(
            "А",
            "Абакан",
        ),
        TitleAndCity(
            "А",
            "Абакан",
        ),
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
                    nameCity = item.city.toString()
                )
            }
        }
    }
}

data class TitleAndCity(
    val title: String,
    val city: String
)

@Preview
@Composable
fun PreviewChooseCityScreen(){
    MegahandTheme {
        ChooseCityScreen()
    }
}