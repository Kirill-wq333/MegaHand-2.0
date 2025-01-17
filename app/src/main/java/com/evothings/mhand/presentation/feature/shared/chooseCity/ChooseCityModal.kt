package com.evothings.mhand.presentation.feature.shared.chooseCity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.ChooseCityScreen
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

@Preview
@Composable
private fun ModalPreview() {
    MegahandTheme {
        Surface {
            ChooseCityModal(
                modifier = Modifier.padding(24.dp),
                onDismiss = {},
                onChoose = {}
            )
        }
    }
}

@Composable
fun ChooseCityModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onChoose: (String) -> Unit
) {

    var selectedCity by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        TopBar(
            onBack = onDismiss
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.medium)
        )
        ChooseCityScreen(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            markChosen = false,
            onChoose = { selectedCity = it }
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.large)
        )
        Actions(
            isChooseButtonEnabled = selectedCity.isNotEmpty(),
            onCancel = onDismiss,
            onClickChoose = { onChoose(selectedCity) }
        )
    }

}

@Composable
private fun TopBar(
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_chevron_left),
            tint = MaterialTheme.colorScheme.secondary,
            backgroundColor = MaterialTheme.colorScheme.secondary.copy(0.05f),
            onClick = onBack
        )
        Text(
            text = stringResource(R.string.choose_city_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun Actions(
    isChooseButtonEnabled: Boolean,
    onCancel: () -> Unit,
    onClickChoose: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            text = stringResource(id = R.string.cancel),
            backgroundColor = Color.Transparent,
            borderColor = MaterialTheme.colorScheme.secondary.copy(0.1f),
            onClick = onCancel
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.choose),
            backgroundColor = MaterialTheme.colorScheme.primary,
            isEnabled = isChooseButtonEnabled,
            textColor = ColorTokens.Graphite,
            onClick = onClickChoose
        )
    }
}