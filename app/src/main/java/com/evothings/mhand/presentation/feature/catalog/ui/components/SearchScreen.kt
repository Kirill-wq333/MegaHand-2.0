package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.evothings.domain.feature.catalog.model.SearchHint
import com.evothings.domain.feature.catalog.model.SubcategoryHint
import com.evothings.domain.feature.catalog.model.TextHint

@Composable
fun SearchScreen(
    hints: List<SearchHint>,
    onClick: (SearchHint) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        itemsIndexed(hints) { index, item ->
            when (item) {
                is TextHint -> {
                    HintItem(
                        text = item.text,
                        onClick = { onClick(item) },
                        selectionRange =  item.selectionRange
                    )
                    if (index < hints.size - 1){
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = colorScheme.secondary.copy(0.05f))
                        )
                    }
                }
                is SubcategoryHint -> {
                    HintsListAsyncImage(
                        model = item.imageLink,
                        contentDescription = "clothes",
                        nameCategory = item.title,
                        descriptionCategory = item.subtitle,
                        onClick = { onClick(item) }
                    )
                    if (index < hints.size - 1){
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = colorScheme.secondary.copy(0.05f))
                        )
                    }
                }

            }
        }
    }

    
}

@Composable
fun HintItem(
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

