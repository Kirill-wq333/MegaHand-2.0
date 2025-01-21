package com.evothings.mhand.presentation.feature.home.ui.components.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun UpdateAvailableBottomSheet(
    onDismiss: () -> Unit,
    onClickUpdate: () -> Unit
) {

    MhandModalBottomSheet(
        onDismissRequest = onDismiss
    ) { hide ->
        UpdateAvailableModal(
            modifier = Modifier.modalBottomSheetPadding(),
            onDismiss = hide,
            onClickUpdate = { onClickUpdate(); hide() },
            version = "1.0.0"
        )
    }

}

@Composable
private fun UpdateAvailableModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onClickUpdate: () -> Unit,
    version: String
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.paddings.extraGiant),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.update_available_image),
            contentDescription = null,
            modifier = Modifier
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = version,
                color = colorScheme.secondary.copy(.5f),
                style = MegahandTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Text(
                text = stringResource(R.string.update_available_modal_title),
                color = colorScheme.secondary,
                style = MegahandTypography.headlineLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
            Text(
                text = stringResource(R.string.update_available_subtitle),
                color = colorScheme.secondary,
                style = MegahandTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
            ) {
                Button(
                    modifier = Modifier
                        .weight(.5f),
                    text = stringResource(R.string.update_available_dismiss_button),
                    textColor = colorScheme.secondary,
                    borderColor = colorScheme.secondary.copy(.1f),
                    onClick = onDismiss
                )
                Button(
                    modifier = Modifier
                        .weight(.5f),
                    text = stringResource(R.string.update_available_proceed_button),
                    textColor = colorScheme.secondary,
                    backgroundColor = colorScheme.primary,
                    onClick = onClickUpdate
                )
            }
        }
    }

}

@Preview
@Composable
private fun UpdateAvailableModalPreview() {
    MegahandTheme {
        Surface {
            UpdateAvailableModal(
                onDismiss = {},
                onClickUpdate = {},
                version = "1.0.0"
            )
        }
    }
}