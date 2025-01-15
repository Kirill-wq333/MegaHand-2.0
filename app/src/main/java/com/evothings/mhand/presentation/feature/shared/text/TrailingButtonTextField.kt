package com.evothings.mhand.presentation.feature.shared.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.SmallButton
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun TrailingButtonTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "",
    labelStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    labelColor: Color = MaterialTheme.colorScheme.secondary.copy(0.6f),
    colorPrize: Color = MaterialTheme.colorScheme.secondary.copy(0.6f),
    buttonLabel: String,
    placeholder: String = "",
    maxLength: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
    onClickTrailingButton: () -> Unit,
    visiblePrize: Boolean = false
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(9.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (label.isNotEmpty()) {
                Text(
                    text = label,
                    style = labelStyle,
                    color = labelColor,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.small))
            if (visiblePrize) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_prize),
                    contentDescription = null,
                    tint = colorPrize,
                    modifier = Modifier
                        .size(16.dp)
                )
            }
        }
        MTextField(
            value = value,
            placeholder = placeholder,
            maxLength = maxLength,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            trailing = {
                SmallButton(
                    text = buttonLabel,
                    backgroundColor = MaterialTheme.colorScheme.secondary.copy(0.05f),
                    onClick = onClickTrailingButton
                )
            }
        )
    }
}

@Preview
@Composable
private fun TrailingButtonTextFieldPreview() {
    MegahandTheme {
        Surface {
            val fieldValue = remember { mutableStateOf("") }
            TrailingButtonTextField(
                value = fieldValue.value,
                label = "Test label",
                buttonLabel = "Test",
                onValueChange = { fieldValue.value = it },
                onClickTrailingButton = {}
            )
        }
    }
}
