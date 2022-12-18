package com.application.letitsnow

import android.content.Context

class WeatherSharedPreferences(context: Context) {

    companion object {
        const val NIGHT_MODE = "NIGHT_MODE"
        const val TOWN = "town"
        const val TEMPERATURE = "temperature"
        const val EMPTY = ""
    }

    val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun getTown(): String? {
        return preferences.getString(TOWN, EMPTY)
    }

    fun setTown(value: String) {
        with(preferences.edit()) {
            putString(TOWN, value)
            apply()
        }
    }

    fun clearTown() {
        with(preferences.edit()) {
            remove(TOWN).clear()
            apply()
        }
    }

    fun getTemperature(): String? {
        return preferences.getString(TEMPERATURE, EMPTY)
    }

    fun setTemperature(value: String) {
        with(preferences.edit()) {
            putString(TEMPERATURE, value)
            apply()
        }
    }

    fun clearTemperature() {
        with(preferences.edit()) {
            remove(TEMPERATURE).clear()
            apply()
        }
    }

    fun getThemeMode(): Boolean {
        return preferences.getBoolean(NIGHT_MODE, false)
    }

    fun setThemeMode(value: Boolean) {
        with(preferences.edit()) {
            putBoolean(NIGHT_MODE, value)
            apply()
        }
    }

}