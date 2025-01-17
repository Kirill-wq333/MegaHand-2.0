package com.evothings.mhand.presentation.feature.Category.components.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.mhand.presentation.feature.catalog.ui.components.categories.CategoryItem
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun CategoriesGrid(
    categories: List<ProductCategory>,
    onClickCategory: (ProductCategory) -> Unit
){

    val gridHeight = remember {
        val verticalPadding = 10 * 2
        val spacing = 12 * 2
        val productCardHeight = 100

        (verticalPadding + spacing + productCardHeight * 2).dp
    }

    LazyVerticalGrid(
        modifier = Modifier
            .height(gridHeight),
        columns = GridCells.Fixed(3),
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.tiny),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.tiny)
    ){

        items(categories) { categoryItem ->
            CategoryItem(
                onClick = {onClickCategory(categoryItem)},
                title = categoryItem.title,
                image = categoryItem.photoLink.orEmpty()
            )
        }

    }
}

