package com.example.scoutweatherinterview.feature.weather.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun setIsFahrenheit(isFahrenheit: Boolean)
    fun getIsFahrenheit(): Flow<Boolean>
}
