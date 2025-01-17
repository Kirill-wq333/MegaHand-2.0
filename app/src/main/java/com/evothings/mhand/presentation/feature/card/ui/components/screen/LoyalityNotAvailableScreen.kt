package com.evothings.mhand.presentation.feature.card.ui.components.screen

import android.widget.Toast
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun LoyalityNotAvailableScreen(
    onNotify: () -> Unit
) {

    val context = LocalContext.current

    var enableNotifyButton by remember { mutableStateOf(true) }

    Content(
        enableNotifyButton = enableNotifyButton,
        onNotify = {
            enableNotifyButton = false
            Toast.makeText(
                context,
                context.getString(R.string.loyality_snackbar_message),
                Toast.LENGTH_LONG
            ).show()
            onNotify()
        },
    )

}

@Composable
private fun Content(
    enableNotifyButton: Boolean,
    onNotify: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.spacers.large
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_no_card),
            tint = colorScheme.secondary.copy(0.4f),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Text(
            text = stringResource(R.string.loyality_not_available_in_your_city),
            style = typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = colorScheme.secondary
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.medium)
        )
        Text(
            text = stringResource(R.string.loyality_description),
            style = typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = colorScheme.secondary.copy(0.6f)
        )
        Spacer(
            modifier = Modifier
                .height(MaterialTheme.spacers.extraLarge)
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.notify_loyality),
            borderColor = colorScheme.secondary.copy(0.1f),
            isEnabled = enableNotifyButton,
            backgroundColor = Color.Transparent,
            onClick = onNotify
        )
    }
}

@Preview
@Composable
private fun LoyalityScreenPreview() {
    MegahandTheme {
        Surface {
            LoyalityNotAvailableScreen(
                onNotify = {}
            )
        }
    }
}