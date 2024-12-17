package com.evothings.widget.viewmodel.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class WidgetKeyDataStore : DataStore<Int> {

    override val data: Flow<Int>
        get() = flow { emit(Random.nextInt()) }

    override suspend fun updateData(transform: suspend (t: Int) -> Int): Int = Random.nextInt()

}