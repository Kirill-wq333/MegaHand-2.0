package com.evothings.mhand.presentation.feature.shared.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LabelTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    labelStyle: TextStyle = typography.bodyLarge,
    labelColor: Color = colorScheme.secondary.copy(0.6f),
    placeholder: String = "",
    isError: Boolean = false,
    errorText: String = "",
    maxLength: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
) {
    LabelTextField(
        modifier = modifier,
        value = value,
        placeholder = placeholder,
        isError = isError,
        errorText = errorText,
        maxLength = maxLength,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = labelStyle,
                color = labelColor,
                modifier = Modifier.padding(horizontal = 6.dp)
            )
        }
    )
}

@Composable
private fun LabelTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    isError: Boolean = false,
    errorText: String = "",
    maxLength: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(9.dp),
        horizontalAlignment = Alignment.Start
    ) {
        label.invoke()
        MTextField(
            value = value,
            placeholder = placeholder,
            errorState = isError,
            readOnly = readOnly,
            errorText = errorText,
            maxLength = maxLength,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )
    }
}