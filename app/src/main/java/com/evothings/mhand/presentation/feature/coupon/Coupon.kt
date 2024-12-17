package com.evothings.mhand.presentation.feature.coupon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.coupon.components.TextFieldNumber
import com.evothings.mhand.presentation.feature.shared.text.MTextField
import com.evothings.mhand.presentation.feature.shared.text.TrailingButtonTextField
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun Coupon(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clip(shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp
            ))
    ){
        Content(
            heading = "Получить 300 ₽",
            informationText = "Еще не совершал покупку в Мегахенд? Заполни форму ниже соверши первую покупку с выгодой!"
        )
    }
}

@Composable
private fun Content(
    heading: String,
    informationText: String
){
    val fieldValue = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(MaterialTheme.paddings.extraGiant),
        horizontalAlignment = Alignment.Start
    ) {
        TextItem(
            text = heading,
            color = colorScheme.secondary,
            fontSize = 20.sp,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
            fontWeight = FontWeight.W500
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        TextItem(
            text = informationText,
            color = colorScheme.secondary.copy(0.4f),
            fontSize = 16.sp,
            fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
            fontWeight = FontWeight.W400
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        NameAndSurnameTextField()
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        NumberPhoneTextField()
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        TrailingButtonTextField(
            value = fieldValue.value,
            label = "Test label",
            buttonLabel = "Test",
            onValueChange = { fieldValue.value = it },
            onClickTrailingButton = {}
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Button()
    }
}

@Composable
fun NameAndSurnameTextField(){
    val value = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MTextField(
            modifier = Modifier
                .width(166.dp),
            value = value.value,
            errorState = true,
            errorText = "Like a leafs",
            placeholder = "Some text",
            onValueChange = { value.value = it }
        )
        MTextField(
            modifier = Modifier
                .width(166.dp),
            value = value.value,
            errorState = true,
            errorText = "Like a leafs",
            placeholder = "Some text",
            onValueChange = { value.value = it }
        )
    }
}

@Composable
fun NumberPhoneTextField(){
    TextFieldNumber(
        phone = stringResource(R.string.phone_number),
        placeholder = "+7 (___) ___-__-__",
    )
}


@Composable
private fun Button(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.primary,
                shape = shapes.small
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Получить",
            color = colorScheme.secondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
            modifier = Modifier
                .padding(MaterialTheme.paddings.extraLarge)
        )
    }
}

@Composable
private fun TextItem(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    fontFamily: FontFamily
){
    Text(
        text = text,
        textAlign = TextAlign.Start,
        color = color,
        fontSize = fontSize,
        fontFamily = fontFamily,
        fontWeight = fontWeight
    )
}

@Preview
@Composable
fun PreviewCoupon(){
    MegahandTheme {
        Coupon()
    }
}