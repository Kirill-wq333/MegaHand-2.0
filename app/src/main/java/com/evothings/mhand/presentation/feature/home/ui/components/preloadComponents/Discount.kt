package com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun Discount(
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
