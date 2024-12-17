package com.evothings.mhand.presentation.feature.shared.text.transform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.evothings.mhand.presentation.feature.shared.text.util.NumberSeparator
import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds

private class RubleVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text.trim()
        if (text.text.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        val splitHundreds = originalText.splitHundreds(NumberSeparator.SPACE)
        val resultText = "$splitHundreds ₽"

        return TransformedText(
            text = AnnotatedString(resultText),
            offsetMapping = RubleOffsetMapping(originalText, resultText)
        )
    }

    inner class RubleOffsetMapping(original: String, transformed: String) : OffsetMapping {


        private val originalLength = original.length
        private val digitIndexes = findDigitIndexes(original, transformed)

        private fun findDigitIndexes(firstString: String, secondString: String): List<Int> {
            val indexes = mutableListOf<Int>()
            var currentIndex = 0
            for (digit in firstString) {
                val index = secondString.indexOf(digit, currentIndex)
                if (index != -1) {
                    indexes.add(index)
                    currentIndex = index + 1
                } else {
                    return emptyList()
                }
            }
            return indexes
        }

        override fun originalToTransformed(offset: Int): Int {
            if (offset >= originalLength) {
                return digitIndexes.last() + 1
            }
            return digitIndexes[offset]
        }

        override fun transformedToOriginal(offset: Int): Int {
            return digitIndexes.indexOfFirst { it >= offset }.takeIf { it != -1 } ?: originalLength
        }

    }

}

@Composable
fun rememberRubleVisualTransformation(): VisualTransformation =
    remember { RubleVisualTransformation() }