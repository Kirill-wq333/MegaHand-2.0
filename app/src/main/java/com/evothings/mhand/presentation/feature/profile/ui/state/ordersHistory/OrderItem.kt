package com.evothings.mhand.presentation.feature.profile.ui.state.ordersHistory

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.evothings.mhand.R
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.evothings.domain.feature.profile.model.Order
import com.evothings.domain.feature.profile.model.OrderHistoryProduct
import com.evothings.domain.util.Mock
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.SmallIconButton
import com.evothings.mhand.presentation.feature.shared.text.util.NumberSeparator
import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun OrderItem(
    model: Order,
    onClickProduct: (Int) -> Unit,
    onCopyTrack: (String) -> Unit,
    onContinueCheckout: () -> Unit,
    onClickPayOrder: () -> Unit
) {

    val isExpanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.secondary.copy(0.05f),
                shape = MaterialTheme.shapes.large
            )
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacers.large)
        ) {

            Heading(
                status = model.status,
                displayStatus = model.statusDisplay,
                orderId = model.orderId,
                isExpanded = isExpanded.value,
                onExpand = { isExpanded.value = !isExpanded.value }
            )

            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.large)
            )

            AnimatedVisibility(
                visible = isExpanded.value,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                OrderInfo(
                    orderDate = model.orderDate,
                    cost = model.cost,
                    trackNumbers = model.trackNumbers,
                    onCopyTrack = onCopyTrack
                )
            }

            AnimatedProducts(
                isExpanded = isExpanded.value,
                products = model.products,
                onClickProduct = onClickProduct
            )

            when(model.status) {
                Order.Status.CREATED -> {
                    ContinueCheckoutButton(
                        modifier = Modifier
                            .padding(top = MaterialTheme.spacers.large),
                        onClick = onContinueCheckout
                    )
                }

                Order.Status.PAYMENT_AWAIT -> {
                    PayOrderButton(
                        modifier = Modifier
                            .padding(top = MaterialTheme.spacers.large),
                        onClickPay = onClickPayOrder
                    )
                }

                else -> {}
            }

        }
    }
}

@Composable
private fun Heading(
    status: Order.Status,
    displayStatus: String,
    orderId: String,
    isExpanded: Boolean,
    onExpand: () -> Unit
) {

    val icon = remember(isExpanded) {
        if (isExpanded) R.drawable.ic_chevron_top else R.drawable.ic_chevron_bottom
    }

    val formattedStatus = remember {
        when(status) {
            Order.Status.CREATED -> "Не оформлен"
            Order.Status.PAYMENT_AWAIT -> "Не оплачен"
            else -> displayStatus
        }
    }

    val isAttentionStatus = remember {
        status == Order.Status.CREATED || status == Order.Status.PAYMENT_AWAIT
    }
    val statusTextColor =
        if (isAttentionStatus) colorScheme.error else colorScheme.secondary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onExpand
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(0.8f)) {
                Text(
                    text = formattedStatus,
                    style = typography.headlineMedium,
                    color = statusTextColor
                )
                Spacer(
                    modifier = Modifier
                        .height(MaterialTheme.spacers.normal)
                )
                Text(
                    text = "№$orderId",
                    style = typography.bodyMedium,
                    color = colorScheme.secondary
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                tint = colorScheme.secondary.copy(
                    alpha = if (isExpanded) 1.0f else 0.6f
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
        }

    }
}

@Composable
private fun HeadingProductsList(
    list: List<OrderHistoryProduct>
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small),
        verticalAlignment = Alignment.CenterVertically
    ) {

        repeat(list.size.coerceAtMost(3)) { i ->
            val item = remember { list[i].photo }

            ProductPhoto(
                modifier = Modifier.size(50.dp),
                link = item,
                onClick = {}
            )
        }

        if (list.size > 3) {
            val remainingItems = remember { list.size - 3 }
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = colorScheme.secondary.copy(0.1f),
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "+$remainingItems",
                    style = typography.bodyLarge
                )
            }
        }

    }
}

@Composable
private fun AnimatedProducts(
    isExpanded: Boolean,
    products: List<OrderHistoryProduct>,
    onClickProduct: (Int) -> Unit
) {
    AnimatedContent(
        targetState = isExpanded,
        label = "AnimatedProducts"
    ) { expanded ->
        if (expanded) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(
                    space = MaterialTheme.spacers.small
                )
            ) {
                items(products) { item ->
                    ProductPhoto(
                        modifier = Modifier.size(100.dp),
                        link = item.photo,
                        onClick = { onClickProduct(item.id) }
                    )
                }
            }
        } else {
            HeadingProductsList(
                list = products
            )
        }
    }
}

@Composable
private fun ProductPhoto(
    modifier: Modifier,
    link: String?,
    onClick: () -> Unit
) {
    AsyncImage(
        model = link,
        placeholder = painterResource(id = R.drawable.image_placeholder),
        error = painterResource(id = R.drawable.no_photo_placeholder),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    )
}

@Composable
private fun OrderInfo(
    orderDate: String,
    cost: Int,
    trackNumbers: List<String>,
    onCopyTrack: (String) -> Unit
) {
    val trackNumberIsEmptyOrInvalid = remember {
        trackNumbers.isEmpty() || trackNumbers.any { false }
    }

    Column {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OrderInfoItem(
                title = stringResource(R.string.order_date),
                subtitle = orderDate
            )
            OrderInfoItem(
                title = stringResource(R.string.order_cost),
                subtitle = "${cost.splitHundreds(NumberSeparator.SPACE)} ₽"
            )
            if (trackNumberIsEmptyOrInvalid) {
                Text(
                    text = stringResource(R.string.order_no_track_status),
                    fontSize = 15.sp,
                    style = typography.bodyLarge,
                    color = colorScheme.secondary
                )
            } else {
                trackNumbers.forEach { track ->
                    OrderInfoItem(
                        title = stringResource(R.string.order_track),
                        subtitle = track,
                        onCopy = { onCopyTrack(track) }
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Text(
            text = stringResource(id = R.string.order_items),
            style = typography.labelLarge,
            color = colorScheme.secondary
        )
        Spacer(
            modifier = Modifier.height(15.dp)
        )
    }
}

@Composable
private fun OrderInfoItem(
    title: String,
    subtitle: String,
    onCopy: () -> Unit = {}
) {

    Column {
        Text(
            text = title,
            style = typography.bodyMedium,
            color = colorScheme.secondary.copy(0.4f)
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.tiny)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = subtitle,
                fontSize = 15.sp,
                style = typography.bodyLarge,
                color = colorScheme.secondary
            )
            Spacer(
                modifier = Modifier
                    .width(MaterialTheme.spacers.tiny)
            )
            SmallIconButton(
                icon = ImageVector.vectorResource(id = R.drawable.ic_copy),
                iconPadding = 0.dp,
                tint = colorScheme.secondary,
                onClick = onCopy
            )
        }
    }
}

@Composable
private fun ContinueCheckoutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.continue_checkout_button),
        textColor = colorScheme.secondary,
        backgroundColor = colorScheme.onSecondary,
        borderColor = colorScheme.secondary.copy(0.1f),
        onClick = onClick
    )
}

@Composable
private fun PayOrderButton(
    modifier: Modifier = Modifier,
    onClickPay: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.pay_expired_order_button),
        textColor = ColorTokens.Graphite,
        backgroundColor = colorScheme.primary,
        onClick = onClickPay
    )
}

@Preview
@Composable
private fun OrderItemPreview() {
    MegahandTheme(false) {
        Surface {
            OrderItem(
                model = Mock.demoOrder,
                onClickProduct = {},
                onCopyTrack = {},
                onClickPayOrder = {},
                onContinueCheckout = {}
            )
        }
    }
}