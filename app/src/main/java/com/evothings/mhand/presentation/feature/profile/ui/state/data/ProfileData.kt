package com.evothings.mhand.presentation.feature.profile.ui.state.data

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.profile.ui.state.data.components.Block
import com.evothings.mhand.presentation.feature.profile.ui.state.data.components.BlockCashback
import com.evothings.mhand.presentation.feature.profile.ui.state.data.components.Data
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun ProfileDataScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorScheme.onSecondary)
    ) {
        ProfileData()
    }
}

@Composable
fun ProfileData(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.profile_onboarding_banner),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.paddings.extraLarge)
                    .clip(shape = MegahandShapes.large)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.paddings.extraGiant)
        ) {
            Action()
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Data(
                nameAndSurName = "Иванов Иван"
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.mega))
            Referral()
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.mega))
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row() {
                    Button(
                        text = stringResource(R.string.logout),
                        textColor = colorScheme.secondary,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.paddings.extraLarge))
                    Button(
                        text = stringResource(R.string.delete_account),
                        textColor = colorScheme.secondary,
                        onClick = {}
                    )

                }
            }
        }
    }

}

@Composable
fun Referral(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
    ) {
        BlockCashback(
            cashback = 5
        )
        Block(
            text = stringResource(R.string.shipping_address),
            content = {}
        )
        Block(
            text = "5GH78",
            visiblePrize = true,
            visibleText = true,
            content = {}
        )
    }
}

@Composable
fun Action(
    modifier: Modifier = Modifier
) {
    Row {
        Button(
            text = stringResource(R.string.user_data_chip),
            textColor = colorScheme.secondary,
            borderColor = colorScheme.primary,
            onClick = {}
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.small))
        Button(
            text = stringResource(R.string.orders_history_chip),
            textColor = colorScheme.secondary,
            borderColor = colorScheme.secondary.copy(.1f),
            onClick = {}
        )
    }
}
@Preview
@Composable
private fun ProfileDataPreview() {
    MegahandTheme {
        ProfileDataScreen()
    }
}