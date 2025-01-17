package com.evothings.mhand.presentation.feature.home.ui.components.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
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
            onClickUpdate = { onClickUpdate(); hide() }
        )
    }

}

@Composable
private fun UpdateAvailableModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onClickUpdate: () -> Unit
) {

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.update_available_image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.mega)
        )
        Text(
            text = stringResource(R.string.update_available_modal_title),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.medium)
        )
        Text(
            text = stringResource(R.string.update_available_subtitle),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.weight(0.5f),
                text = stringResource(R.string.update_available_dismiss_button),
                backgroundColor = Color.Transparent,
                borderColor = MaterialTheme.colorScheme.secondary.copy(0.1f),
                onClick = onDismiss
            )
            Spacer(
                modifier = Modifier
                    .width(MaterialTheme.spacers.medium)
            )
            Button(
                modifier = Modifier.weight(0.5f),
                text = stringResource(R.string.update_available_proceed_button),
                backgroundColor = MaterialTheme.colorScheme.primary,
                textColor = ColorTokens.Graphite,
                onClick = onClickUpdate
            )
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
                onClickUpdate = {}
            )
        }
    }
}