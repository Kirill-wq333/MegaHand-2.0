package com.evothings.mhand.presentation.feature.profile.ui.state.ordersHistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
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
        IssueARefund()
    }
}


@Composable
fun IssueARefund(
    modifier: Modifier = Modifier
) {
    val reason = listOf<String>(
        "Товар не подошел",
        "Ошибка с размером",
        "Другое"
    )

    val chosenIndex = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.paddings.extraGiant),
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
    }

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