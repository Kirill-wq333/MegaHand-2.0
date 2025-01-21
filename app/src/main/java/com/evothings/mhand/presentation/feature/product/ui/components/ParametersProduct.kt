package com.evothings.mhand.presentation.feature.product.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.evothings.domain.feature.product.model.ProductProperty
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
@Composable
fun ParametersProduct(
    size: String,
    color: String,
    quality: String,
    series: String,
    idStyle: String,
    dataPublished: String,
    properties: List<ProductProperty>,
    inStock: Boolean
) {
    val mergedProperties = remember {
        val staticProperties = listOf(
            ProductProperty(name = "Размер", value = size),
            ProductProperty(name = "Качество", value = quality),
            ProductProperty(name = "Серия", value = series),
            ProductProperty(name = "ID Стиля", value = idStyle),
            ProductProperty(name = "Дата выхода", value = dataPublished),
            ProductProperty(name = "Цвет", value = color),
        )

        staticProperties + properties
    }
    if (inStock) {
        repeat(mergedProperties.size) { i ->
            val propertyItem = remember { mergedProperties[i] }
            ParametersProductItem(
                text = propertyItem.name,
                secondText = propertyItem.value
            )
        }
    } else {
        Text(
            text = stringResource(id = R.string.out_of_stock),
            style = typography.headlineMedium,
            fontSize = 24.sp,
            color = colorScheme.secondary
        )
    }
}





@Composable
fun ParametersProductItem(
    text: String,
    secondText: String
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = colorScheme.secondary.copy(0.6f),
            style = MegahandTypography.bodyLarge
        )
        Text(
            text = secondText,
            color = colorScheme.secondary,
            style = MegahandTypography.bodyLarge
        )
    }
}

@Preview
@Composable
fun PreviewParameterProductItem(){
    MegahandTheme {
        ParametersProductItem(
            text = stringResource(R.string.id_style_product),
            secondText = stringResource(R.string.quality_product)
        )
    }
}