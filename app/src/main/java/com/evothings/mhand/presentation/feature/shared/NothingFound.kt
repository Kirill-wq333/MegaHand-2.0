package com.evothings.mhand.presentation.feature.shared

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun NothingFound() {
    Spacer(
        modifier = Modifier
            .height(MaterialTheme.spacers.extraLarge)
    )
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.nothing_found),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.secondary.copy(0.6f)
    )
}