package com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Preview
@Composable
fun OtherBottomSheetPreview(){
    OtherBottomSheet()
}


@Composable
fun OtherBottomSheet(){
    Content(
        getCash = "Получить 300 Р",
    )
}

@Composable
private fun Content(
    getCash: String,
    selected: Boolean = true
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.paddings.extraGiant,
                    start = MaterialTheme.paddings.extraGiant,
                    bottom = MaterialTheme.paddings.extraLarge,
                    end = MaterialTheme.paddings.extraGiant
                ),

        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small)
            ) {
                RowBottomItems(
                    text = stringResource(R.string.favourites_screen_title),
                    text2 = stringResource(R.string.shops),
                )
                RowBottomItems(
                    text = stringResource(R.string.news_screen_title),
                    text2 = stringResource(R.string.about_service),
                )
                RowBottomItems(
                    text = stringResource(R.string.vacancies),
                    text2 = stringResource(R.string.help),
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
            BottonFillMaxSizeItem(
                text = getCash,
                backgroundColor = colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
            BottonFillMaxSizeItem(
                text = if (selected) stringResource(R.string.light_theme_name,R.string.theme_switch)
                else stringResource(R.string.dark_theme_name,R.string.theme_switch),
                backgroundColor = colorScheme.secondary.copy(0f),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

}

@Composable
private fun RowBottomItems(
    text: String,
    text2: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BottomItem(
            text = text,
            modifier = Modifier
                .padding(
                    vertical = 42.dp,
                    horizontal = MaterialTheme.paddings.extraLarge
                )
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.small))
        BottomItem(
            text = text2,
            modifier = Modifier
                .padding(
                    vertical = 42.dp,
                    horizontal = MaterialTheme.paddings.extraLarge
                )
        )
    }

}

@Composable
private fun BottonFillMaxSizeItem(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color
){

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = MegahandShapes.medium
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            color = colorScheme.secondary,
            style = MegahandTypography.labelLarge,
        )
    }

}

@Composable
private fun BottomItem(
    modifier: Modifier = Modifier,
    text: String,
){

    Box(
        modifier = Modifier
            .background(
                color = colorScheme.primaryContainer.copy(.05f),
                shape = MegahandShapes.medium
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            color = colorScheme.secondary,
            style = MegahandTypography.labelLarge,
            modifier = modifier
        )
    }

}