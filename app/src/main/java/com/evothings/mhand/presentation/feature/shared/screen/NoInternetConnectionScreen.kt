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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun NoInternetConnectionScreen(
    onReload: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_signal_off),
                contentDescription = null,
                tint = colorScheme.secondary,
                modifier = Modifier
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Text(
                text = stringResource(R.string.no_internet_connection),
                color = colorScheme.secondary,
                style = MegahandTypography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
            Text(
                text = stringResource(R.string.no_internet_subtitle),
                textAlign = TextAlign.Center,
                color = colorScheme.secondary.copy(.6f),
                style = MegahandTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Button(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.Transparent,
                borderColor = colorScheme.secondary.copy(0.15f),
                text = stringResource(R.string.reload_page),
                onClick = onReload
            )
        }
    }

}

@Preview
@Composable
private fun NoInternetConnectionPreview() {
    MegahandTheme {
        Surface {
            NoInternetConnectionScreen {}
        }
    }
}