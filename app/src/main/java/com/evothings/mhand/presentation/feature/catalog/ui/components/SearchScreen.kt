package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.catalog.model.SearchHint
import com.evothings.domain.feature.catalog.model.SubcategoryHint
import com.evothings.domain.feature.catalog.model.TextHint
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogCallback
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogUiState
import com.evothings.mhand.presentation.feature.catalog.viewmodel.CatalogContract
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun SearchScreen(

    hints: List<SearchHint>,
    onClick: (SearchHint) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(hints) { item ->
            when (item) {
                is TextHint -> {
                    HintItem(
                        text = item.text,
                        onClick = { onClick(item) },
                        selectionRange =  item.selectionRange
                    )
                }
                is SubcategoryHint -> {
                    HintsListAsyncImage(
                        model = item.imageLink,
                        contentDescription = "clothes",
                        nameCategory = item.title,
                        descriptionCategory = item.subtitle,
                        onClick = { onClick(item) }
                    )
                }

            }
        }
    }

    
}

@Composable
fun HintItem(
    modifier: Modifier = Modifier,
    text: String,
    selectionRange: List<Int>,
    onClick: () -> Unit
    ) {
    val textWithSelection = buildAnnotatedString {
        text.forEachIndexed { index, char ->
            if (index in selectionRange) {
                withStyle(
                    style = SpanStyle(fontWeight = FontWeight.Medium)
                ) { append(char) }
            } else {
                append(char)
            }
        }
    }
    HintsList(
        text = textWithSelection,
        onClick = onClick
    )
}

