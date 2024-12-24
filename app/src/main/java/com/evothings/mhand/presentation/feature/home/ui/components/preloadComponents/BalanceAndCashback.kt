package com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.text.util.NumberSeparator
import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun Price(
    price: Double,
    cashback: Double
) {
    Row(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.paddings.extraLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextItem(
            text = "${price.splitHundreds(NumberSeparator.SPACE)} ₽",
            fontSize = 18.sp,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
            fontWeight = FontWeight.W500
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.normal))
        if (cashback > 0) {
            Cashback(cashback = cashback)
        }
    }
}

@Composable
private fun Cashback(
    cashback: Double
) {

    Box(
        modifier = Modifier
            .background(
                color = colorScheme.inverseSurface.copy(0.1f),
                shape = MegahandShapes.large
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
                tint = colorScheme.inverseSurface
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.tiny))
            TextItem(
                text = "${cashback.toInt().splitHundreds(NumberSeparator.SPACE)} ₽",,
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