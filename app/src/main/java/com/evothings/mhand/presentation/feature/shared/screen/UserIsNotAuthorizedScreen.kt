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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import com.evothings.mhand.R
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun UserIsNotAuthorized(
    onClickAuthorize: () -> Unit
) {

    val text =
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.secondary
                )
            ) {
                append(stringResource(id = R.string.not_authorized_subtitle1))
            }
            withStyle(
             style = SpanStyle(
                 color = MaterialTheme.colorScheme.secondary.copy(.6f)
             )
            ) {
                append(
                    stringResource(
                        id = R.string.not_authorized_subtitle2,
                    )
                )
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.spacers.extraLarge
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(100.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_account),
            tint = colorScheme.secondary.copy(0.4f),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Column() {
            Text(
                text = stringResource(R.string.not_authorized_title),
                style = typography.headlineSmall,
                color = colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
            Text(
                text = text,
                style = MegahandTypography.bodyLarge,
                color = colorScheme.secondary.copy(0.6f),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Button(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.login),
            backgroundColor = colorScheme.primary,
            textColor = ColorTokens.Graphite,
            onClick = onClickAuthorize
        )
    }

}


@Preview
@Composable
private fun PreviewUserIsNotAuthorized() {
    MegahandTheme {
        Surface {
            UserIsNotAuthorized { }
        }
    }
}