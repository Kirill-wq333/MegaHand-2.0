package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogCallback
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogUiState
import com.evothings.mhand.presentation.feature.catalog.viewmodel.CatalogContract
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun SearchScreen(
    state: CatalogContract.State,
    uiState: CatalogUiState,
    callback: CatalogCallback
){
    Box(
        modifier = Modifier
            .background(color = colorScheme.onSecondary)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.paddings.extraLarge)
        ) {
            SearchBar(
                modifier = Modifier.padding(12.dp),
                query = uiState.query,
                enableBackButton = (state !is CatalogContract.State.Search),
                onChangeQuery = callback::onChangeQuery,
                onSearch = callback::search,
                onBack = callback::resetState
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            HintsList(text = stringResource(R.string.pants))
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorScheme.secondary.copy(0.05f))
            )
            HintsList(text = stringResource(R.string.pants))
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorScheme.secondary.copy(0.05f))
            )
            HintsList(text = stringResource(R.string.pants))
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorScheme.secondary.copy(0.05f))
            )
            HintsList(text = stringResource(R.string.pants))
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            HintsListAsyncImage(
                model = "https://s3-alpha-sig.figma.com/img/bbc9/b4f4/3bcb0ac3ea5a25f8bff886fa37da5c5d?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=AQyMhZk8IhlCmT-0PnG5aoBYVuy~QU7XpvBX-zmheXo2yABCgT18nTSVlhQl3CkzxGcYl76a2mNMvFZI4-r6lHfd65DnBkRiCRkEACHRekl7AlIEHN6u2NftRCb53RWgs1sEYVQPw72e4mjR5PYQGMFroWxnhQ2cqYuERt7DlbEAh73hw2GWau5fx5m~6eDPs2h7MR5z3x07un84o7AIJ~Jwo6p-EwD9OmrGLCj4su1zIi3oZNzOKwJX0bbfZx571gxUejCZLRkw0vxL~AMa7EAL2ywi-UpROw0zbEM8xWrE~MzQhriBATrIAU1tEbjx9iP2tWq2V6FzS1zy9UTRqw__",
                contentDescription = "clothes",
                nameCategory = "Одежда",
                descriptionCategory = "Одежда – Верхняя одежда"
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorScheme.secondary.copy(0.05f))
            )
            HintsListAsyncImage(
                model = "https://s3-alpha-sig.figma.com/img/bbc9/b4f4/3bcb0ac3ea5a25f8bff886fa37da5c5d?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=AQyMhZk8IhlCmT-0PnG5aoBYVuy~QU7XpvBX-zmheXo2yABCgT18nTSVlhQl3CkzxGcYl76a2mNMvFZI4-r6lHfd65DnBkRiCRkEACHRekl7AlIEHN6u2NftRCb53RWgs1sEYVQPw72e4mjR5PYQGMFroWxnhQ2cqYuERt7DlbEAh73hw2GWau5fx5m~6eDPs2h7MR5z3x07un84o7AIJ~Jwo6p-EwD9OmrGLCj4su1zIi3oZNzOKwJX0bbfZx571gxUejCZLRkw0vxL~AMa7EAL2ywi-UpROw0zbEM8xWrE~MzQhriBATrIAU1tEbjx9iP2tWq2V6FzS1zy9UTRqw__",
                contentDescription = "clothes",
                nameCategory = "Одежда",
                descriptionCategory = "Одежда – Верхняя одежда"
            )
        }

    }
}

