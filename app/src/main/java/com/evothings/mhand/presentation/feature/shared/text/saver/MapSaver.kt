package com.evothings.mhand.presentation.feature.shared.text.saver

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope

sealed class MapSaver<T> : Saver<T, Int> {

    private val map: HashMap<Int, T> = hashMapOf()

    override fun SaverScope.save(value: T): Int? {
        val key = value.hashCode()
        map.putIfAbsent(key, value)
        return key
    }

    override fun restore(value: Int): T? {
        return map[value]
    }

}

data object StringSaver : MapSaver<String>()

data object BooleanSaver : MapSaver<Boolean>()

data object IntSaver : MapSaver<Int>()

data object AnySaver : MapSaver<Any>()