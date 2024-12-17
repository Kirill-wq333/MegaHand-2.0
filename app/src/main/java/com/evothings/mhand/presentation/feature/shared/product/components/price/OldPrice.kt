package com.evothings.mhand.presentation.feature.shared.product.components.price

//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.material3.Text
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.MaterialTheme.colorScheme
//import androidx.compose.material3.MaterialTheme.typography
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.tooling.preview.Preview
//import com.evothings.mhand.presentation.feature.shared.text.util.NumberSeparator
//import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds
//import com.evothings.mhand.presentation.theme.MegahandTheme
//import com.evothings.mhand.presentation.theme.spacers
//
//@Composable
//fun OldPrice(
//    enabled: Boolean,
//    value: Double,
//    discount: Double,
//    isDiscountPercent: Boolean,
//    size: PriceSize = PriceSize.Small
//) {
//
//    val fontStyle =
//        if (size == PriceSize.Small) typography.bodyMedium else typography.bodyLarge
//
//    val discountChar = if (isDiscountPercent) '%' else '₽'
//
//    Row(
//        modifier = Modifier.alpha(if (enabled) 1.0f else 0.0f),
//        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = value.splitHundreds(NumberSeparator.SPACE),
//            style = fontStyle,
//            textDecoration = TextDecoration.LineThrough,
//            color = colorScheme.secondary.copy(0.4f)
//        )
//        Text(
//            text = "-${discount.splitHundreds(NumberSeparator.SPACE)} $discountChar",
//            style = fontStyle,
//            color = colorScheme.error
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun OldPricePreview() {
//    MegahandTheme {
//        Surface {
//            OldPrice(
//                enabled = true,
//                value = 22300.56,
//                discount = 1500.0,
//                isDiscountPercent = false
//            )
//        }
//    }
//}