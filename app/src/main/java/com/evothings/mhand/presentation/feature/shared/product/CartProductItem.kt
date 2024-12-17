package com.evothings.mhand.presentation.feature.shared.product

//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.res.stringResource
//import com.evothings.mhand.R
//import androidx.compose.ui.res.vectorResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.evothings.domain.feature.product.model.Product
//import com.evothings.domain.util.Mock
//import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
//import com.evothings.mhand.presentation.feature.shared.checkbox.CheckboxChecker
//import com.evothings.mhand.presentation.feature.shared.product.components.CardProductInformation
//import com.evothings.mhand.presentation.feature.shared.product.components.ConditionBadge
//import com.evothings.mhand.presentation.feature.shared.product.components.PhotosSlider
//import com.evothings.mhand.presentation.feature.shared.product.components.ProductPhoto
//import com.evothings.mhand.presentation.feature.shared.product.components.ProductTitle
//import com.evothings.mhand.presentation.feature.shared.product.components.SizeBadge
//import com.evothings.mhand.presentation.feature.shared.text.saver.BooleanSaver
//import com.evothings.mhand.presentation.theme.MegahandTheme
//import com.evothings.mhand.presentation.theme.spacers
//
//@Composable
//fun CartProductItem(
//    modifier: Modifier = Modifier,
//    model: Product,
//    isSelected: Boolean,
//    onSelect: () -> Unit,
//    onClick: () -> Unit,
//    toggleFavourite: () -> Unit,
//    removeFromCart: () -> Unit
//) {
//    when(model.availability){
//        Product.Availability.IN_STOCK -> {
//            InStockCartProductItem(
//                modifier = modifier,
//                model = model,
//                isSelected = isSelected,
//                onSelect = onSelect,
//                onClick = onClick,
//                toggleFavourite = toggleFavourite,
//                removeFromCart = removeFromCart
//            )
//        }
//        Product.Availability.OUT_OF_STOCK -> {
//            OutOfStockCartProductItem(
//                modifier = modifier,
//                model = model,
//                onClick = onClick,
//                toggleFavourite = toggleFavourite,
//                removeFromCart = removeFromCart
//            )
//        }
//    }
//}
//
//
//@Composable
//private fun InStockCartProductItem(
//    modifier: Modifier = Modifier,
//    model: Product,
//    isSelected: Boolean,
//    onSelect: () -> Unit,
//    onClick: () -> Unit,
//    toggleFavourite: () -> Unit,
//    removeFromCart: () -> Unit
//) {
//    Row(
//        modifier = modifier
//            .height(180.dp)
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
//                onClick = onClick
//            )
//    ) {
//        ImageSliderWithCheckbox(
//            modifier = Modifier
//                .weight(0.5f)
//                .fillMaxSize(),
//            images = model.photos,
//            isChecked = isSelected,
//            onCheck = onSelect,
//        )
//
//        Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
//
//        Column(
//            modifier = Modifier
//                .weight(0.5f)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            CardProductInformation(
//                actualPrice = model.actualPrice,
//                oldPrice = model.oldPrice,
//                cashbackPoints = model.cashbackPoints,
//                discount = model.discount,
//                isPercentDiscount = model.isPercentDiscount,
//                title = model.title,
//                titleMaxLines = 2,
//                size = model.size,
//                condition = model.condition,
//                keepOldPrice = false
//            )
//            Actions(
//                isFavourite = model.isFavourite,
//                onClickHeart = toggleFavourite,
//                onClickDelete = removeFromCart
//            )
//        }
//    }
//}
//
//@Composable
//fun OutOfStockCartProductItem(
//    modifier: Modifier = Modifier,
//    model: Product,
//    onClick: () -> Unit,
//    toggleFavourite: () -> Unit,
//    removeFromCart: () -> Unit
//) {
//    Row(
//        modifier = modifier
//            .alpha(0.3f)
//            .height(180.dp)
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
//                onClick = onClick
//            )
//    ) {
//        ProductPhoto(
//            modifier = Modifier
//                .weight(0.5f)
//                .fillMaxSize(),
//            link = model.photos.firstOrNull().orEmpty()
//        )
//
//        Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
//
//        Column(
//            modifier = Modifier
//                .weight(0.5f)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column(
//                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal)
//            ) {
//                Text(
//                    text = stringResource(R.string.out_of_stock),
//                    style = MaterialTheme.typography.headlineSmall,
//                    color = MaterialTheme.colorScheme.secondary
//                )
//                ProductTitle(
//                    value = model.title,
//                    maxLines = 2
//                )
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    SizeBadge(
//                        size = model.size
//                    )
//                    ConditionBadge(
//                        condition = model.condition
//                    )
//                }
//            }
//            Actions(
//                isFavourite = model.isFavourite,
//                onClickHeart = toggleFavourite,
//                onClickDelete = removeFromCart
//            )
//        }
//    }
//}
//
//
//@Composable
//private fun ImageSliderWithCheckbox(
//    modifier: Modifier,
//    images: List<String>,
//    isChecked: Boolean,
//    onCheck: () -> Unit
//) {
//
//    var isCheckedLocal by remember(isChecked) { mutableStateOf(isChecked) }
//
//    Box(
//        modifier = modifier
//    ) {
//        PhotosSlider(
//            modifier = Modifier.fillMaxSize(),
//            images = images
//        )
//        Box(
//            modifier = Modifier
//                .align(Alignment.TopStart)
//                .offset(4.dp, 4.dp)
//                .size(24.dp)
//                .clickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = null,
//                    onClick = { isCheckedLocal = !isCheckedLocal; onCheck() }
//                ),
//            contentAlignment = Alignment.Center
//        ) {
//            CheckboxChecker(
//                isChecked = isCheckedLocal
//            )
//        }
//    }
//}
//
//@Composable
//private fun Actions(
//    isFavourite: Boolean,
//    onClickHeart: () -> Unit,
//    onClickDelete: () -> Unit
//) {
//
//    var isFavouriteLocal by rememberSaveable(isFavourite, BooleanSaver) { mutableStateOf(isFavourite) }
//    val heartBorderColor =
//        if (isFavouriteLocal) MaterialTheme.colorScheme.primary else Color.Transparent
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small)
//    ) {
//        IconButton(
//            icon = ImageVector.vectorResource(id = R.drawable.ic_heart),
//            tint = MaterialTheme.colorScheme.secondary,
//            borderColor = heartBorderColor,
//            backgroundColor = Color.Transparent,
//            onClick = { isFavouriteLocal = !isFavouriteLocal; onClickHeart() }
//        )
//        IconButton(
//            icon = ImageVector.vectorResource(id = R.drawable.ic_trash),
//            tint = MaterialTheme.colorScheme.secondary,
//            borderColor = Color.Transparent,
//            backgroundColor = Color.Transparent,
//            onClick = onClickDelete
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun CardProductItemPreview() {
//    MegahandTheme(false) {
//        Surface {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(190.dp)
//            ) {
//                InStockCartProductItem(
//                    modifier = Modifier
//                        .matchParentSize()
//                        .padding(12.dp),
//                    model = Mock.demoProduct,
//                    isSelected = true,
//                    onSelect = {},
//                    onClick = {},
//                    toggleFavourite = {},
//                    removeFromCart = {}
//                )
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun OutOfStockCardProductItemPreview() {
//    MegahandTheme(false) {
//        Surface {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(190.dp)
//            ) {
//                OutOfStockCartProductItem(
//                    modifier = Modifier
//                        .matchParentSize()
//                        .padding(12.dp),
//                    model = Mock.demoProduct,
//                    onClick = {},
//                    toggleFavourite = {},
//                    removeFromCart = {}
//                )
//            }
//        }
//    }
//}