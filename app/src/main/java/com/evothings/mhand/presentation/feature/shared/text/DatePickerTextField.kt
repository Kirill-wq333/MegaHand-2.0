package com.evothings.mhand.presentation.feature.shared.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.evothings.mhand.R
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.domain.util.DateFormat
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.icon.SmallIconButton
import com.evothings.mhand.presentation.feature.shared.picker.date.DatePicker
import com.evothings.mhand.presentation.feature.shared.text.transform.TextMasks
import com.evothings.mhand.presentation.feature.shared.text.transform.rememberMaskVisualTransformation
import com.evothings.mhand.presentation.theme.MegahandTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@Preview
@Composable
private fun PickerTextFieldPreview() {
    MegahandTheme(true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background),
            contentAlignment = Alignment.Center
        ) {

            var value by remember { mutableStateOf("") }

            DatePickerTextField(
                modifier = Modifier.padding(24.dp),
                date = value,
                onDateChange = { value = it }
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    modifier: Modifier = Modifier,
    date: String,
    dateFormat: String = DateFormat.FULL_DATE,
    placeholder: String = "",
    onDateChange: (String) -> Unit
) {


    var isFocused by remember { mutableStateOf(false) }

    var isPickerBottomSheetExpanded by remember { mutableStateOf(false) }

    val formattedDate = remember(date) {
        date // Remove all possible dividers
            .replace(".", "")
            .replace("-", "")
            .replace("/", "")
    }

    val dateTimestampMillis: Long = remember(date) {
        val format = DateTimeFormatter.ofPattern(dateFormat)
        runCatching {
            val parsed = format.parse(date)
            val localDateTime = LocalDate.from(parsed)
            val epochSecond = localDateTime.toEpochDay()
            val epochMillis = TimeUnit.DAYS.toMillis(epochSecond)
            epochMillis
        }.getOrDefault(System.currentTimeMillis())
    }

    MTextField(
        modifier = modifier,
        value = formattedDate,
        onValueChange = { newDate ->
            val internalFormat = DateTimeFormatter.ofPattern("ddMMyyyy")
            val outputFormat = DateTimeFormatter.ofPattern(dateFormat)
            val result = runCatching {
                val parsedDate = internalFormat.parse(newDate)
                outputFormat.format(parsedDate)
            }.getOrDefault(newDate)

            onDateChange(result)
        },
        placeholder = placeholder,
        maxLength = 8,
        visualTransformation = rememberMaskVisualTransformation(mask = TextMasks.date),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        onFocusChange = { focusState -> isFocused = focusState },
        trailing = {
            SmallIconButton(
                icon = ImageVector.vectorResource(id = R.drawable.ic_calendar),
                iconPadding = 0.dp,
                tint = colorScheme.secondary.copy(
                    alpha = if (isFocused) 1.0f else 0.4f
                ),
                onClick = { isPickerBottomSheetExpanded = !isPickerBottomSheetExpanded }
            )
        }
    )

    if (isPickerBottomSheetExpanded) {
        MhandModalBottomSheet(
            onDismissRequest = { isPickerBottomSheetExpanded = false }
        ) { hide ->
            Box(
                modifier = Modifier.padding(
                    top = 15.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 24.dp
                ),
                contentAlignment = Alignment.Center
            ) {
                DatePicker(
                    dateToSet = dateTimestampMillis,
                    onConfirmDate = { millis ->
                        val timestampSeconds = TimeUnit.MILLISECONDS.toSeconds(millis)
                        val format = DateTimeFormatter.ofPattern(dateFormat)
                        val dateTime =
                            LocalDateTime.ofEpochSecond(timestampSeconds, 0, ZoneOffset.UTC)
                        val outputDate = format.format(dateTime)

                        onDateChange(outputDate)
                        hide()
                    },
                    onHide = hide
                )
            }
        }
    }

}