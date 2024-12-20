package com.evothings.mhand.presentation.feature.shared.screen.chooseCity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes


@Composable
fun TextFieldAndTitle() {

    val title = listOf(
        "Всё",
        "А",
        "Б",
        "В",
        "Г",
        "Д",
        "Ж",
        "З",
        "И",
        "К",
        "Л",
        "М",
        "Н",
        "О",
        "П",
        "Р",
        "С",
        "Т",
        "У",
        "Ф",
        "Х",
        "Ц",
        "Ч",
        "Ш",
        "Щ",
        "Э",
        "Ю",
        "Я",
    )

    val value = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(MaterialTheme.paddings.extraLarge)

    ) {
        MTextField(
            value = value.value,
            errorState = false,
            errorText = "",
            placeholder = "Поиск по названию",
            onValueChange = { value.value = it }
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small)
        ) {
            items(title) { item ->
                BoxTitleItem(text = item)
            }
        }
    }
}

@Composable
fun BoxTitleItem(
    text: String
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = colorScheme.primary,
                shape = MegahandShapes.medium
            )
    ) {
        Text(
            text = text,
            color = colorScheme.secondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.paddings.extraLarge,
                    horizontal = MaterialTheme.paddings.giant
                )
        )
    }
}


@Preview
@Composable
fun PreviewTextFieldAndTitle(){
    MegahandTheme {
        TextFieldAndTitle()
    }
}