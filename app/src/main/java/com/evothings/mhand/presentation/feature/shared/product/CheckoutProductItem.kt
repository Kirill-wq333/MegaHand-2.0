package com.evothings.mhand.presentation.feature.shared.product

//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.evothings.domain.feature.product.model.Product
//import com.evothings.domain.util.Mock
//import com.evothings.mhand.presentation.feature.shared.product.components.CardProductInformation
//import com.evothings.mhand.presentation.feature.shared.product.components.ProductPhoto
//import com.evothings.mhand.presentation.theme.MegahandTheme
//
//@Composable
//fun CheckoutProductItem(
//    modifier: Modifier = Modifier,
//    model: Product,
//    onClick: () -> Unit
//) {
//    Row(
//        modifier = modifier
//            .height(100.dp)
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
//                onClick = onClick
//            )
//    ) {
//        ProductPhoto(
//            modifier = Modifier
//                .width(100.dp)
//                .fillMaxSize(),
//            link = model.photos.firstOrNull().orEmpty()
//        )
//        Spacer(
//            modifier = Modifier.width(15.dp)
//        )
//        CardProductInformation(
//            modifier = Modifier.weight(0.7f),
//            actualPrice = model.actualPrice,
//            oldPrice = model.oldPrice,
//            cashbackPoints = model.cashbackPoints,
//            discount = model.discount,
//            isPercentDiscount = model.isPercentDiscount,
//            title = model.title,
//            size = "",
//            titleMaxLines = 2,
//            condition = "",
//            showSizeAndCondition = false,
//            keepOldPrice = false
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun OrderHistoryProductItemPreview() {
//    MegahandTheme(false) {
//        Surface {
//            Box(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                CheckoutProductItem(
//                    modifier = Modifier.padding(12.dp),
//                    model = Mock.demoProduct,
//                    onClick = {}
//                )
//            }
//        }
//    }
//}