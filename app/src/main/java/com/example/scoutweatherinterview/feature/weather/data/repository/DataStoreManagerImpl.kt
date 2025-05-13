package com.example.scoutweatherinterview.feature.weather.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.scoutweatherinterview.feature.weather.domain.repository.DataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class DataStoreManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): DataStoreManager {
    companion object {
        val USING_FAHRENHEIT = booleanPreferencesKey("USING_FAHRENHEIT")
    }
    override suspend fun setIsFahrenheit(isFahrenheit: Boolean) {
        context.dataStore.edit { pref ->
            pref[USING_FAHRENHEIT] = isFahrenheit
        }
    }

    override fun getIsFahrenheit() : Flow<Boolean> {
        return context.dataStore.data.map { pref ->
            pref[USING_FAHRENHEIT] ?: true
        }
    }
}