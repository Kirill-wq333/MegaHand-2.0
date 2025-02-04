package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.mhand.presentation.theme.paddings

@Composable
fun SubCategory(
    subcategories: List<ProductCategory>,
    onClick: (ProductCategory) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.paddings.extraLarge)
    ) {
        items(subcategories) { item ->
            HintsList(
                text = item.title,
                onClick = { onClick(item) }
            )
        }
    }

}
