package com.evothings.mhand.presentation.feature.shared.text.util

@JvmInline
value class NumberSeparator private constructor(val value: Char) {
    companion object {
        val COMMA = NumberSeparator(',')
        val SPACE = NumberSeparator(' ')
    }
}