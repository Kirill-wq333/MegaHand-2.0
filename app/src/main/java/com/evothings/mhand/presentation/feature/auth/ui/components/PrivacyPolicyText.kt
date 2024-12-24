package com.evothings.mhand.presentation.feature.auth.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.evothings.mhand.R

@Composable
fun PrivacyPolicyText(
    buttonLabel: String,
    onClick: () -> Unit
) {

    val text =
        buildAnnotatedString {
            append(
                stringResource(
                    id = R.string.auth_privacy_policy_text,
                    buttonLabel)
            )
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.secondary
                )
            ) {
                append(stringResource(id = R.string.privacy_policy))
            }
        }

    Text(
        text = text,
        color = MaterialTheme.colorScheme.secondary.copy(0.4f),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    )

}