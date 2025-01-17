package com.evothings.mhand.presentation.feature.cart.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun AuthorizationRequired(
    onClickLogin: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.authorization_required_heading),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.normal)
        )
        Text(
            text = stringResource(R.string.auth_required_text),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary.copy(0.6f),
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.user_login),
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = ColorTokens.Graphite,
            onClick = onClickLogin
        )
    }
}