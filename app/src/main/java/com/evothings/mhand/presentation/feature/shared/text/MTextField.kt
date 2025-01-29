package com.evothings.mhand.presentation.feature.shared.text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.spacers

@Preview
@Composable
private fun MTextFieldPreview() {
    MegahandTheme {
        val value = remember { mutableStateOf("") }
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.White)
        ) {
            MTextField(
                value = value.value,
                errorState = true,
                errorText = "Like a leafs",
                placeholder = "Some text",
                onValueChange = { value.value = it }
            )
        }
    }
}

@Composable
fun MTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    errorState: Boolean = false,
    errorText: String = "",
    placeholder: String = "",
    readOnly: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    onFocusChange: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
    alignment: Alignment = Alignment.Center
) {

    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        BasicTextField(
            modifier = modifier
                .onFocusChanged { state ->
                    val newState = state.isFocused
                    isFocused = newState
                    onFocusChange(newState)
                },
            value = value,
            onValueChange = { input ->
                if (input.length <= maxLength)
                    onValueChange(input)
            },
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            readOnly = readOnly,
            textStyle = typography.labelLarge
                .copy(
                    color = colorScheme.secondary
                ),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            cursorBrush = SolidColor(colorScheme.secondary),
            decorationBox = { innerTextField ->
                MTextFieldDecorationBox(
                    errorState = errorState,
                    isFocused = isFocused,
                    enablePlaceholder = (value.isEmpty() && !isFocused),
                    placeholderText = placeholder,
                    leading = leading,
                    trailing = trailing,
                    innerTextField = innerTextField,
                    alignment = alignment
                )
            }
        )
        if (errorState) {
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.normal)
            )
            Text(
                text = errorText,
                color = colorScheme.error,
                style = typography.bodyMedium
            )
        }
    }

}

@Composable
private fun MTextFieldDecorationBox(
    errorState: Boolean,
    isFocused: Boolean,
    enablePlaceholder: Boolean,
    placeholderText: String,
    alignment: Alignment,
    leading: (@Composable () -> Unit)?,
    trailing: (@Composable () -> Unit)?,
    innerTextField: @Composable () -> Unit
) {

    val borderColor =
        when {
            errorState -> colorScheme.error
            isFocused -> colorScheme.primary
            else -> colorScheme.secondary.copy(alpha = 0.2f)
        }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shapes.medium
            ),
        contentAlignment = alignment
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (enablePlaceholder) {
                    Text(
                        text = placeholderText,
                        color = colorScheme.secondary.copy(0.4f),
                        style = typography.labelLarge
                    )
                }
                innerTextField.invoke()
            }
            if (trailing != null) {
                Spacer(
                    modifier = Modifier
                        .width(MaterialTheme.spacers.small)
                )
                trailing.invoke()
            }
        }
    }

}