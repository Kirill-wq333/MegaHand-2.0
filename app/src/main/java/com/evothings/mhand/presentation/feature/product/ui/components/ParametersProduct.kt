package com.evothings.mhand.presentation.feature.product.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun ParametersProduct(
    sizeProduct: String,
    qualityProduct: String,
    seriesProduct: String,
    styleProduct: String,
    releaseDateProduct: String,
    colorProduct: String
){
    Column(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.paddings.extraGiant),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal)
    ) {
        ParametersProductItem(
            text = stringResource(R.string.size_product),
            secondText = sizeProduct
        )
        ParametersProductItem(
            text = stringResource(R.string.quality_product),
            secondText = qualityProduct
        )
        ParametersProductItem(
            text = stringResource(R.string.series_product),
            secondText = seriesProduct
        )
        ParametersProductItem(
            text = stringResource(R.string.id_style_product),
            secondText = styleProduct
        )
        ParametersProductItem(
            text = stringResource(R.string.release_date_product),
            secondText = releaseDateProduct
        )
        ParametersProductItem(
            text = stringResource(R.string.color_product),
            secondText = colorProduct
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
        TextItem(
            text = text,
            color = colorScheme.secondary.copy(0.6f),
        )
        TextItem(
            text = secondText,
            color = colorScheme.secondary
        )
    }
}


@Composable
private fun TextItem(
    text: String,
    color: Color,
) {
    Text(
        text = text,
        color = color,
        style = MegahandTypography.bodyLarge
    )
}



@Preview
@Composable
fun PreviewParameterProductItem(){
    ParametersProductItem(
        text = stringResource(R.string.id_style_product),
        secondText = stringResource(R.string.quality_product)
    )
}