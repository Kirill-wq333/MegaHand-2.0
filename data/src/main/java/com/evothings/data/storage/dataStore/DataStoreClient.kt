package com.evothings.data.storage.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreClient(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "appSettings")

    fun getBoolean(key: String, default: Boolean = false): Flow<Boolean> =
        getValue(booleanPreferencesKey(key), default)

    suspend fun setBoolean(key: String, value: Boolean) {
        setValue(booleanPreferencesKey(key), value)
    }

    fun getString(key: String, default: String = ""): Flow<String> =
        getValue(stringPreferencesKey(key), default)

    suspend fun setString(key: String, value: String) =
        setValue(stringPreferencesKey(key), value)

    private fun <T> getValue(key: Preferences.Key<T>, default: T): Flow<T> =
        context.dataStore.data.map { preferences ->
            preferences[key] ?: default
        }

    private suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { mutPrefs ->
            mutPrefs[key] = value
        }
    }

}