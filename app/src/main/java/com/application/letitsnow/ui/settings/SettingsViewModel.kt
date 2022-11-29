package com.application.letitsnow.ui.settings

import android.content.Context
import androidx.lifecycle.*
import com.application.letitsnow.WeatherRepository
import com.application.letitsnow.ui.start.StartViewModel

class SettingsViewModel(private val weatherRepository: WeatherRepository?) : ViewModel() {



    companion object {
        fun factory(
            weatherRepository: WeatherRepository? = null,
            context: Context? = null
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return StartViewModel(weatherRepository, context) as T
            }
        }
    }
}