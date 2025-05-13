package com.example.scoutweatherinterview.core.logging

import android.util.Log

class LoggerImpl : Logger {
    companion object {
        private const val TAG = "SCOUT_LOGGER"
        private const val ERROR = "_ERROR"
    }

    override fun onScreenViewed(screenName: String) {
        Log.i(TAG, "onScreenViewed: $screenName")
    }

    override fun onFahrenheitToggled(isChecked: Boolean) {
        Log.i(TAG, "onFahrenheitToggled isChecked: $isChecked")
    }

    override fun logError(errorName: String) {
        Log.i(TAG + ERROR, "logError: $errorName")
    }
}
