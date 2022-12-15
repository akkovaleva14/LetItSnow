package com.application.letitsnow

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate


class App : Application() {

    private val NIGHT_MODE = "NIGHT_MODE"
    private var isNightModeEnabled = false

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun getApp(): App? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()

        /*if (App.getApp()?.isNightModeEnabled() == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }*/
        val context: Context = App.applicationContext()
        val mPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        isNightModeEnabled = mPrefs.getBoolean(NIGHT_MODE, false)
    }

    fun isNightModeEnabled(): Boolean {
        return isNightModeEnabled
    }

    fun setIsNightModeEnabled(isNightModeEnabled: Boolean) {
        this.isNightModeEnabled = isNightModeEnabled
        val mPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = mPrefs.edit()
        editor.putBoolean(NIGHT_MODE, isNightModeEnabled)
        editor.apply()
    }
}