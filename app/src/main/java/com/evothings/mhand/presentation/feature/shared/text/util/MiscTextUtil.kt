package com.evothings.mhand.presentation.feature.shared.text.util

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

fun String.cleanPhoneNumber(): String = this
    .replace("+", "")
    .replace("(", "")
    .replace(")", "")
    .replace("-", "")
    .replace(" ", "")

fun Int.addLeadingZero(): String = if (this < 10) "0$this" else this.toString()

fun Spanned.toAnnotateString(
    baseSpanStyle: SpanStyle?,
    linkColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        val spanned = this@toAnnotateString
        append(spanned.toString())
        baseSpanStyle?.let { addStyle(it, 0, length) }
        getSpans(0, spanned.length, Any::class.java).forEach { span ->
            val start = getSpanStart(span)
            val end = getSpanEnd(span)
            when (span) {
                is StyleSpan -> when (span.style) {
                    Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                    Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                    Typeface.BOLD_ITALIC -> addStyle(
                        SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic),
                        start,
                        end
                    )
                }
                is UnderlineSpan -> addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
                is ForegroundColorSpan -> addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
                is URLSpan -> {
                    addStyle(
                        SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = linkColor
                        ), start, end
                    )
                    addStringAnnotation("url", span.url, start, end)
                }
            }
        }
    }
}