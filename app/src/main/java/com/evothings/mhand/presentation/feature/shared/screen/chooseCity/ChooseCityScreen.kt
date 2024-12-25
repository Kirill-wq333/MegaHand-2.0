package com.evothings.mhand.presentation.feature.shared.screen.chooseCity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.components.TextFieldAndTitle
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.components.TitleCity
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

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
