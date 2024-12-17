package com.evothings.mhand.presentation.feature.shared.screen

import androidx.compose.foundation.layout.Arrangement
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
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun NoInternetConnectionScreen(
    onReload: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_signal_off),
            contentDescription = null,
            tint = colorScheme.secondary.copy(0.4f),
            modifier = Modifier.size(100.dp)
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Text(
            text = stringResource(R.string.no_internet_connection),
            textAlign = TextAlign.Center,
            style = typography.headlineSmall
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.medium)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.no_internet_subtitle),
            textAlign = TextAlign.Center,
            color = colorScheme.secondary.copy(0.6f),
            style = typography.labelLarge
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.Transparent,
            borderColor = colorScheme.secondary.copy(0.15f),
            text = stringResource(R.string.reload_page),
            onClick = onReload
        )
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