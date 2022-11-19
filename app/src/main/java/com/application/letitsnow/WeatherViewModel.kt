package com.application.letitsnow

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.application.letitsnow.data.Weather
import com.application.letitsnow.network.NetworkState
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository?) : ViewModel() {

    val town = ObservableField<String>()

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

    companion object {
        fun factory(
            weatherRepository: WeatherRepository?
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WeatherViewModel(weatherRepository) as T
            }
        }
    }
}