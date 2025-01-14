package com.evothings.mhand.presentation.feature.catalog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastCbrt
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.feature.shared.text.SearchField
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    enableBackButton: Boolean,
    onChangeQuery: (String) -> Unit,
    onSearch: () -> Unit,
    onBack: () -> Unit
){

    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isFocused || enableBackButton) {
            IconButton(
                icon = ImageVector.vectorResource(id = R.drawable.ic_chevron_left),
                tint = MaterialTheme.colorScheme.secondary,
                backgroundColor = MaterialTheme.colorScheme.secondary.copy(0.05f),
                onClick = onBack
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraLarge))
        }
        SearchField(
            query = query,
            placeholder = stringResource(R.string.search_bar_placeholder),
            onChangeFocusState = { state -> isFocused = state },
            onClickSearch = onSearch,
            onValueChange = onChangeQuery,
        )
    }

}

