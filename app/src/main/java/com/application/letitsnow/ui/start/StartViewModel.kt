package com.application.letitsnow.ui.start

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.application.letitsnow.WeatherRepository
import com.application.letitsnow.data.Weather
import com.application.letitsnow.network.NetworkState
import kotlinx.coroutines.launch

class StartViewModel(private val weatherRepository: WeatherRepository?) : ViewModel() {

    val town = ObservableField<String>()
    val temp = ObservableField<String>()

    private val _weather = MutableLiveData<Weather?>()
    val weather: LiveData<Weather?> = _weather

    fun getCurrentWeather() {
        viewModelScope.launch {
            weatherRepository?.let {

                town.get()?.let {
                    when (val townWeather =
                        weatherRepository.getTownWeather(it)) {
                        is NetworkState.Success -> {
                            _weather.postValue(townWeather.data)
                            temp.set(townWeather.data.current.temp_c.toString())
                            Log.i("sasha", "getCurrentWeather: ${townWeather.data}")
                        }
                        is NetworkState.Error -> {
                            Log.e("ERROR", "NetworkState error")
                        }
                    }

                }

            }
        }

    }

    fun onTownChanged(value: String) {
        town.set(value)
        getCurrentWeather()
    }

    companion object {
        fun factory(
            weatherRepository: WeatherRepository?
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return StartViewModel(weatherRepository) as T
            }
        }
    }
}