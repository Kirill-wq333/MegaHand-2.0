package com.evothings.mhand.presentation.feature.product.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.feature.shared.product.components.ProductPhoto
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun SliderPhoto(
    model: List<String>
){
    val pagerState = rememberPagerState(
        pageCount = { model.size }
    )

    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        state = pagerState,
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.spacers.medium
        ),
        pageSpacing = MaterialTheme.spacers.small,
        pageSize = PageSize.Fixed(345.dp)
    ) { index ->

        val item = remember { model[index] }

        ProductPhoto(
            modifier = Modifier.size(345.dp),
            link = item
        )
    }
}