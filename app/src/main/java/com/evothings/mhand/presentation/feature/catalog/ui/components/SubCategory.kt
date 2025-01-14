package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun SubCategory() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.onSecondary)
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.paddings.extraLarge)
        ) {
            SearchBar(
                query = "",
                enableBackButton = true,
                onSearch = {},
                onChangeQuery = {},
                onBack = {}
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            HintsList(text = stringResource(R.string.all_clothes))
            HintsList(text = stringResource(R.string.new_items))
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            HintsList(text = stringResource(R.string.t_shirts))
            HintsList(text = stringResource(R.string.shirts))
            HintsList(text = stringResource(R.string.pants))
            HintsList(text = stringResource(R.string.trousers))
            HintsList(text = stringResource(R.string.jeans))
            HintsList(text = stringResource(R.string.turtlenecks))
            HintsList(text = stringResource(R.string.jackets))
            HintsList(text = stringResource(R.string.coast))
        }
    }
}


@Preview
@Composable
fun PreviewSubCategory(){
    MegahandTheme {
        SubCategory()
    }
}