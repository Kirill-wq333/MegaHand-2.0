package com.evothings.mhand.presentation.feature.product.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun OrderSheet(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = MaterialTheme.paddings.giant,
                    horizontal = MaterialTheme.paddings.extraGiant
                )
        ) {
            OrderSheetitem()
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
            OrderSheetButton()
        }
    }
}



@Composable
fun OrderSheetitem(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Price(
            price = "12 010",
            cashback = "578"
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        Discount(
            discountPercent = "40",
            discount = "22 340"
            )
    }

}


@Composable
private fun Discount(
    discount: String,
    discountPercent: String
) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.paddings.extraLarge
            )
    ) {

        Text(
            text = discount,
            color = colorScheme.secondary.copy(0.6f),
            textDecoration = TextDecoration.LineThrough,
            fontSize = 12.sp,
            fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
            fontWeight = FontWeight.W400
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.small))
        Text(
            text = "-$discountPercent%",
            color = colorScheme.error,
            fontSize = 12.sp,
            fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
            fontWeight = FontWeight.W400
        )

    }
}

@Composable
private fun Price(
    price: String,
    cashback: String
) {
    Row(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.paddings.extraLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextItem(
            text = "$price₽",
            fontSize = 18.sp,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
            fontWeight = FontWeight.W500
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        Cashback(cashback = cashback)
    }
}

@Composable
private fun Cashback(
    cashback: String
) {

    Box(
        modifier = Modifier
            .background(
                color = Color(0xFF0BD20B).copy(0.1f),
                shape = shapes.large
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.paddings.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_prize),
                contentDescription = null,
                tint = Color(0xFF0BD20B)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.tiny))
            TextItem(
                text = "$cashback₽",
                fontSize = 12.sp,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                fontWeight = FontWeight.W400
            )
        }
    }

}

@Composable
private fun TextItem(
    text: String,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    fontFamily: FontFamily
){
    Text(
        text = text,
        color = colorScheme.secondary,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily
    )
}

@Composable
private fun OrderSheetButton(){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        ButtonBuy(text = "Купить")
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        ButtonSave()
    }
}

@Composable
private fun ButtonBuy(
    text: String
){
    Box(
        modifier = Modifier
            .width(294.dp)
            .height(44.dp)
            .background(color = Color(0xFFFFE600), shape = shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = colorScheme.secondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500)))
        )
    }
}

@Composable
private fun ButtonSave() {
    Box(
        modifier = Modifier
            .size(42.dp)
            .border(
                width = 1.dp,
                color = colorScheme.secondary.copy(0.1f),
                shape = shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_favourite),
            contentDescription = "favorite",
            tint = colorScheme.secondary,
            modifier = Modifier
                .size(24.dp)
        )
    }
}
