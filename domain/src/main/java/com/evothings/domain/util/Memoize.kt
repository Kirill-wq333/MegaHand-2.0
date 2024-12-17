package com.evothings.domain.util

private class Memoize0<out R : Any>(val f: () -> R) : () -> R {
    private lateinit var value: R
    override fun invoke(): R {
        if (!this::value.isInitialized)
            value = f()
        return value
    }
}

fun <R : Any> (() -> R).memoize(): () -> R = Memoize0(this)