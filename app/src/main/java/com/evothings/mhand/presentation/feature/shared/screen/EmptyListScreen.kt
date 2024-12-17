package com.evothings.mhand.presentation.feature.shared.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun EmptyListScreen(
    modifier: Modifier = Modifier,
    text: String,
    onClickOpenCatalog: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.spacers.extraLarge,
                vertical = MaterialTheme.spacers.large
            ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.empty_screen_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.small)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.open_catalog_button),
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = ColorTokens.Graphite,
            onClick = onClickOpenCatalog
        )

    }
}