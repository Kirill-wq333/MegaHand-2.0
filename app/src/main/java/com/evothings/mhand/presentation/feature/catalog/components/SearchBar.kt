package com.evothings.mhand.presentation.feature.catalog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.paddings

@Composable
fun Searcbar(){

    val state = remember { mutableStateOf(TextFieldValue("")) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){

        Row(
            modifier = Modifier
                .padding(MaterialTheme.paddings.extraLarge)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box() {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_left),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(MaterialTheme.paddings.large)
                )
            }
            OutlinedTextField(
                value = state.value,
                onValueChange = { value ->
                    state.value = value
                },
                placeholder = {
                    Text(
                        text = "Искать в Мегахенд",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                        fontWeight = FontWeight.W400
                    )
                },
                trailingIcon = {
                    IconItem(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                        contentDescription = "close"
                    )
                    IconItem(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                        contentDescription = "search"
                    )
                }
            )
        }

    }
}

@Composable
fun IconItem(
    contentDescription: String?,
    imageVector: ImageVector
){
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription
    )
}