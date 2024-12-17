package com.evothings.mhand.presentation.feature.coupon.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers


@Composable
fun TextFieldNumber(
    modifier: Modifier = Modifier,
    phone: String,
    placeholder: String,
){
    val phones = remember{mutableStateOf("")}

    Box(
        modifier = modifier
    ) {

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = phone,
                color = colorScheme.secondary.copy(0.6f),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                modifier = Modifier
                    .padding(start = MaterialTheme.paddings.medium)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
            OutlinedTextField(
                value = phones.value,
                onValueChange = {
                    phones.value = it
                },
                placeholder = {
                    Text(
                        text = placeholder,
                        color = colorScheme.secondary.copy(0.4f),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                        fontWeight = FontWeight.W400
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
        }

    }
}

@Preview
@Composable
fun PreviewTextField(){
    MegahandTheme {
        Column {
            TextFieldNumber(
                placeholder = "+7 (___) ___-__-__",
                phone = stringResource(R.string.phone_number),
            )
        }
    }
}