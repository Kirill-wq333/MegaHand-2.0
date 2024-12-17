package com.evothings.data.utils

internal inline fun <T> foldAuthorized(
    checkIsLoggedIn: () -> Boolean,
    onAuthorized: () -> T,
    onNotAuthorized: () -> T
): T {
    val isLoggedIn = checkIsLoggedIn()
    return if (isLoggedIn) {
        onAuthorized()
    } else {
        onNotAuthorized()
    }
}