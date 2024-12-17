package com.evothings.mhand.presentation.feature.shared.product.components

//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.res.vectorResource
//import androidx.compose.ui.unit.dp
//import com.evothings.mhand.R
//import com.evothings.mhand.presentation.feature.shared.product.components.price.OldPrice
//import com.evothings.mhand.presentation.feature.shared.product.components.price.Price
//import com.evothings.mhand.presentation.theme.spacers
//
//@Composable
//fun CardProductInformation(
//    modifier: Modifier = Modifier,
//    actualPrice: Double,
//    oldPrice: Double,
//    cashbackPoints: Double,
//    discount: Double,
//    isPercentDiscount: Boolean,
//    title: String,
//    titleMaxLines: Int = 1,
//    size: String,
//    condition: String,
//    showSizeAndCondition: Boolean = true,
//    keepOldPrice: Boolean = true
//) {
//    Column(
//        modifier = modifier,
//    ) {
//        Price(
//            value = actualPrice,
//            cashback = cashbackPoints
//        )
//
//        Spacer(modifier = Modifier.height(MaterialTheme.spacers.tiny))
//
//        if (keepOldPrice || discount > 0) {
//            OldPrice(
//                enabled = (discount > 0),
//                value = oldPrice,
//                discount = discount,
//                isDiscountPercent = isPercentDiscount
//            )
//        }
//
//        Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
//
//        ProductTitle(
//            value = title,
//            maxLines = titleMaxLines
//        )
//
//        Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
//
//        if (showSizeAndCondition) {
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                SizeBadge(
//                    size = size
//                )
//                ConditionBadge(
//                    condition = condition
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun SizeBadge(
//    size: String
//) {
//    Row(
//        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.tiny),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            imageVector = ImageVector.vectorResource(id = R.drawable.ic_tag),
//            contentDescription = null,
//            tint = MaterialTheme.colorScheme.secondary,
//            modifier = Modifier.size(15.dp)
//        )
//        Text(
//            text = size,
//            style = MaterialTheme.typography.bodyMedium,
//            color = MaterialTheme.colorScheme.secondary.copy(0.6f)
//        )
//    }
//}
//
//@Composable
//fun ConditionBadge(
//    condition: String
//) {
//    Row(
//        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.tiny),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            imageVector = ImageVector.vectorResource(id = R.drawable.ic_star),
//            contentDescription = null,
//            tint = MaterialTheme.colorScheme.secondary,
//            modifier = Modifier.size(15.dp)
//        )
//        Text(
//            text = condition,
//            style = MaterialTheme.typography.bodyMedium,
//            color = MaterialTheme.colorScheme.secondary.copy(0.6f)
//        )
//    }
//}