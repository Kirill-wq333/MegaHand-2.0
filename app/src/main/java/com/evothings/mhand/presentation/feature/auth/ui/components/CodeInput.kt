package com.evothings.mhand.presentation.feature.auth.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import com.evothings.mhand.presentation.feature.auth.ui.components.modifier.shake
import com.evothings.mhand.presentation.utils.sdkutil.Vibrator

@Composable
fun AuthCodeInput(
    code: String,
    isErrorState: Boolean,
) {

    val context = LocalContext.current

    LaunchedEffect(isErrorState) {
        if (isErrorState) {
            Vibrator.makeVibration(150L, context)
        }
    }

    BasicTextField(
        modifier = Modifier.shake(enabled = isErrorState),
        value = code,
        onValueChange = {},
        readOnly = true,
        decorationBox = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    repeat(4) { index ->
                        InputItem(
                            index = index,
                            value = code,
                            isErrorState = isErrorState
                        )
                    }
                }
                if (isErrorState) {
                    Text(
                        text = stringResource(R.string.incorrect_code),
                        color = colorScheme.error,
                        style = typography.bodyLarge
                    )
                }
            }
        }
    )
}

@Composable
private fun InputItem(
    index: Int,
    value: String,
    isErrorState: Boolean
) {

    val char = remember(value) {
        when {
            index >= value.length -> "_"
            else -> value[index].toString()
        }
    }

    val focused = remember(value) {
        value.length == index || index <= value.length
    }

    val textColor =
        when {
            isErrorState -> colorScheme.error
            focused -> colorScheme.secondary
            else -> colorScheme.secondary.copy(0.2f)
        }

    val borderColor =
        when {
            isErrorState -> colorScheme.error
            focused -> colorScheme.primary
            else -> colorScheme.secondary.copy(0.2f)
        }

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char,
            style = typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = textColor,
            modifier = Modifier
                .padding(
                    vertical = 12.5.dp,
                    horizontal = 15.dp
                )
        )
    }

}