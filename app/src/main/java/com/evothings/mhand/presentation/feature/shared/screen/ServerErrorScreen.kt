package com.evothings.mhand.presentation.feature.shared.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun ServerErrorScreen(
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_exclamation),
                contentDescription = null,
                tint = colorScheme.secondary,
                modifier = Modifier
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Text(
                text = stringResource(R.string.server_error_title),
                color = colorScheme.secondary,
                style = MegahandTypography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
            Text(
                text = stringResource(R.string.server_error_subtitle),
                textAlign = TextAlign.Center,
                color = colorScheme.secondary.copy(.6f),
                style = MegahandTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Button(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.reload_page),
                backgroundColor = Color.Transparent,
                borderColor = MaterialTheme.colorScheme.secondary.copy(0.15f),
                onClick = onRefresh
            )
        }
    }
}

@Preview
@Composable
private fun ServerErrorPreview() {
    MegahandTheme {
        Surface {
            ServerErrorScreen {}
        }
    }
}