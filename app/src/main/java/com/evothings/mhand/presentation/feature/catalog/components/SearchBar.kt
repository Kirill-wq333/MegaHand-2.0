package com.evothings.mhand.presentation.feature.catalog.components

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastCbrt
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun Searcbar(){

    val value = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconItem()
        Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraLarge))
        MTextField(
            value = value.value,
            errorState = false,
            errorText = "Like a leafs",
            placeholder = "Some text",
            onValueChange = { value.value = it }
        )
    }

}

@Composable
private fun IconItem(){
    Box(
        modifier = Modifier
            .background(
                color = colorScheme.secondaryContainer.copy(0.05f),
                shape = shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_left),
            contentDescription = "chevron_left",
            modifier = Modifier
                .padding(MaterialTheme.paddings.large)
        )
    }
}