package com.evothings.mhand.presentation.utils.list

fun MutableList<Int>.toggleItem(item: Int) =
    if (item in this) {
        this.remove(item)
    } else {
        this.add(item)
    }