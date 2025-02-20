package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        itemsIndexed(subcategories) { index, item ->
            Column {
                HintsList(
                    text = item.title,
                    onClick = { onClick(item) }
                )
                if (index < subcategories.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = colorScheme.secondary.copy(0.05f)
                    )
                }
            }
        }
    }

}
