package com.example.scoutweatherinterview.core.logging

interface Logger {
    fun onScreenViewed(screenName: String)
    fun onFahrenheitToggled(isChecked: Boolean)
}