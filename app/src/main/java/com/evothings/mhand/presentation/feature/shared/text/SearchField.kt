package com.evothings.mhand.presentation.feature.shared.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import com.evothings.mhand.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.button.icon.SmallIconButton
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    query: String,
    placeholder: String,
    onClickSearch: () -> Unit = {},
    onChangeFocusState: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit
) {

    var isFocused by remember { mutableStateOf(false) }

    MTextField(
        modifier = modifier,
        placeholder = placeholder,
        trailing = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal)
            ) {
                if (isFocused) {
                    SmallIconButton(
                        icon = ImageVector.vectorResource(id = R.drawable.ic_close),
                        iconPadding = 0.dp,
                        tint = colorScheme.secondary.copy(0.6f),
                        backgroundColor = Color.Transparent,
                        onClick = { onValueChange("") },
                    )
                }
                IconButton(
                    icon = ImageVector.vectorResource(id = R.drawable.ic_search),
                    iconPadding = 0.dp,
                    tint = colorScheme.secondary.copy(
                        alpha = if (isFocused) 1.0f else 0.4f
                    ),
                    onClick = onClickSearch,
                )
            }
        },
        value = query,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onClickSearch() }),
        onFocusChange = { state ->
            isFocused = state
            onChangeFocusState(state)
        }
    )

}

@Preview
@Composable
private fun SearchFieldPreview() {
    MegahandTheme(false) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.White)
        ) {
            var value by remember { mutableStateOf("") }

            SearchField(
                modifier = Modifier.padding(10.dp),
                query = value,
                placeholder = "Some search",
                onValueChange = { value = it },
            )
        }
    }
}
