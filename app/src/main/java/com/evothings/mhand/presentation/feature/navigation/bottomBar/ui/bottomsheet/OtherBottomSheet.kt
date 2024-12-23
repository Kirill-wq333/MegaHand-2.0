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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Preview
@Composable
fun OtherBottomSheetPreview(){
    MegahandTheme {
        OtherBottomSheet()
    }
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
            .background(
                color = colorScheme.onSecondary,
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp
                )
                )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.paddings.extraGiant,
                    start = MaterialTheme.paddings.extraGiant,
                    bottom = MaterialTheme.paddings.extraLarge,
                    end = MaterialTheme.paddings.extraGiant
                )
        ) {
            Text(
                text = stringResource(R.string.other),
                textAlign = TextAlign.Start,
                color = colorScheme.secondary,
                style = MegahandTypography.headlineMedium,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small)
            ) {
                RowBottomItems(
                    text = stringResource(R.string.favourites_screen_title),
                    text2 = stringResource(R.string.shops),
                    modifier = Modifier
                        .padding(horizontal = 41.5.dp),
                    modifier2 = Modifier
                        .padding(horizontal = 46.dp)
                )
                RowBottomItems(
                    text = stringResource(R.string.news_screen_title),
                    text2 = stringResource(R.string.about_service),
                    modifier = Modifier
                        .padding(horizontal = 51.5.dp),
                    modifier2 = Modifier
                        .padding(horizontal = 45.dp)
                )
                RowBottomItems(
                    text = stringResource(R.string.vacancies),
                    text2 = stringResource(R.string.help),
                    modifier = Modifier
                        .padding(horizontal = 47.dp),
                    modifier2 = Modifier
                        .padding(horizontal = 52.5.dp)
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
    modifier: Modifier = Modifier,
    modifier2: Modifier = Modifier,
    text: String,
    text2: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomItem(
            text = text,
            modifier = modifier
        )
        BottomItem(
            text = text2,
            modifier = modifier2
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
            modifier = Modifier
                .padding(vertical = MaterialTheme.paddings.extraLarge)
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
                .padding(vertical = MaterialTheme.paddings.extraLarge)
        )
    }

}