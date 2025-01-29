package com.evothings.mhand.presentation.feature.profile.ui.state.ordersHistory

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.evothings.domain.feature.profile.model.OrderHistoryProduct
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Preview
@Composable
private fun IssueARefundPreview() {
    MegahandTheme {
        Surface {
            IssueARefundScreen()
        }
    }
}


@Composable
fun IssueARefundScreen(
    modifier: Modifier = Modifier
) {
    MhandModalBottomSheet(
        onDismissRequest = {}
    ) {
        IssueARefund(
            modifier = Modifier.modalBottomSheetPadding()
        )
    }
}


@Composable
fun IssueARefund(
    modifier: Modifier = Modifier,
) {
    val reason = listOf<String>(
        "Товар не подошел",
        "Ошибка с размером",
        "Другое"
    )

    var card by remember { mutableStateOf("") }

    val chosenIndex = remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ){
        Text(
            text = stringResource(R.string.make_refund_button),
            color = colorScheme.secondary,
            style = MegahandTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Text(
            text = "235252352",
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Text(
            text = "Причина возврата",
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        repeat(reason.size){
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
            ) {
                ReasonForReturn(
                    text = reason[it],
                    chosen = it == chosenIndex.intValue,
                    onClick = { chosenIndex.intValue = it }
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Text(
            text = stringResource(R.string.order_product),
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
        ) {
            items(4) { item ->
                ProductPhoto(
                    modifier = Modifier.size(100.dp),
                    link = "",
                    onClick = {}
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Text(
            text = stringResource(R.string.comment),
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        MTextField(
            modifier = Modifier
                .height(100.dp),
            placeholder = "Номер карты",
            value = card,
            onValueChange = {},
            alignment = Alignment.TopStart
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.make_refund_button),
            textColor = colorScheme.secondary,
            backgroundColor = colorScheme.primary,
            onClick = {}
        )
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
fun ReasonForReturn(
    modifier: Modifier = Modifier,
    chosen: Boolean,
    text: String,
    onClick: () -> Unit
) {
    val checkboxIconResId = if (chosen)
        R.drawable.ic_radio_button_active
    else
        R.drawable.ic_radio_button_disabled

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.extraGiant),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                color = colorScheme.secondary,
                style = MegahandTypography.bodyLarge
            )
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = checkboxIconResId),
                contentDescription = null
            )
        }
    }

}