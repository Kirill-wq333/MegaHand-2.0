package com.evothings.mhand.presentation.feature.Category.components.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import androidx.compose.ui.graphics.painter.Painter
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.mhand.presentation.feature.shared.text.SearchField
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
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

