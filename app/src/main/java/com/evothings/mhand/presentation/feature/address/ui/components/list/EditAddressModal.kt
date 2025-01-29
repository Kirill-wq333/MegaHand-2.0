package com.evothings.mhand.presentation.feature.address.ui.components.list

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.evothings.mhand.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.evothings.mhand.presentation.feature.shared.link.HighLink
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.theme.MegahandTheme

@Composable
fun EditAddressModal(
    modifier: Modifier,
    onEdit: () -> Unit,
    onSetDefault: () -> Unit,
    onDelete: () -> Unit,
) {
    Column(modifier) {
        HighLink(
            text = stringResource(id = R.string.edit),
            enableIcon = true,
            onClick = onEdit
        )
        HighLink(
            text = stringResource(R.string.make_primary),
            enableIcon = false,
            onClick = onSetDefault
        )
        HighLink(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.error)) {
                    append("Удалить")
                }
            },
            enableIcon = false,
            onClick = onDelete,
            visibleHorizontalDivider = false
        )
    }
}

@Preview
@Composable
private fun EditAddressModalPreview() {
    MegahandTheme {
        Surface {
            EditAddressModal(
                modifier = Modifier.modalBottomSheetPadding(),
                onEdit = {},
                onSetDefault = {},
                onDelete = {}
            )
        }
    }
}