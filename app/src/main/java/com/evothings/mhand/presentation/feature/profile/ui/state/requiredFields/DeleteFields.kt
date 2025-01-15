package com.evothings.mhand.presentation.feature.profile.ui.state.requiredFields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.onboarding.model.CardAlignment
import com.evothings.mhand.presentation.feature.onboarding.ui.components.BottomSheetLikeIndicator
import com.evothings.mhand.presentation.feature.profile.viewmodel.ProfileContract
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun DeleteAccount(
    modifier: Modifier = Modifier,
    alignment: CardAlignment
) {

    val indicatorAlign = remember(alignment) {
        if (alignment == CardAlignment.BOTTOM)
            Alignment.TopCenter
        else
            Alignment.BottomCenter
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.onSecondary,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
    ) {
        BottomSheetLikeIndicator(
            modifier = Modifier.align(indicatorAlign)
        )
        Content()
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(MaterialTheme.paddings.extraGiant)
    ) {
        Text(
            text = stringResource(R.string.delete_account_title),
            color = colorScheme.secondary,
            style = MegahandTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Text(
            text = stringResource(R.string.delete_account_notice),
            color = colorScheme.secondary.copy(.6f),
            style = MegahandTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Text(
            text = stringResource(R.string.delete_account_confirmation),
            color = colorScheme.secondary,
            style = MegahandTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.weight(.5f),
                text = stringResource(R.string.cancel),
                textColor = colorScheme.secondary,
                borderColor = colorScheme.secondary.copy(.1f),
                onClick = {}
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
            Button(
                modifier = Modifier.weight(.5f),
                text = stringResource(R.string.delete),
                textColor = colorScheme.onSecondary,
                backgroundColor = colorScheme.error,
                onClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun DeleteAccountPreview() {
    MegahandTheme {
        DeleteAccount(
            alignment = CardAlignment.BOTTOM
        )
    }
}