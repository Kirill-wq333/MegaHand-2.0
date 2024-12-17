package com.evothings.mhand.presentation.feature.product.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun ParametersProduct(){
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal)
    ) {
        ParametersProductItem(
            text = ,
            secondText =
        )
        ParametersProductItem(
            text = ,
            secondText =
        )
        ParametersProductItem(
            text = ,
            secondText =
        )
        ParametersProductItem(
            text = ,
            secondText =
        )
        ParametersProductItem(
            text = ,
            secondText =
        )
        ParametersProductItem(
            text = ,
            secondText =
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
    fontWeight: FontWeight = FontWeight.W400,
    fontFamily: FontFamily = FontFamily(listOf(Font(R.font.golos_400))),
    fontSize: TextUnit = 16.sp
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily
    )
}