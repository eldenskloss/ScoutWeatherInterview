package com.example.scoutweatherinterview.core.logging

import android.util.Log

class LoggerImpl: Logger {
    companion object {
        private const val TAG = "SCOUT_LOGGER"
    }

    override fun onScreenViewed(screenName: String) {
        Log.i(TAG, "onScreenViewed: $screenName")
    }

    override fun onFahrenheitToggled(isChecked: Boolean) {
        Log.i(TAG, "onFahrenheitToggled isChecked: $isChecked")
    }
}