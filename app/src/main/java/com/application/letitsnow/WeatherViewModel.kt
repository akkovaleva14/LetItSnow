package com.application.letitsnow

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.application.letitsnow.data.Weather
import com.application.letitsnow.network.NetworkState
import kotlinx.coroutines.launch
import java.util.*

class WeatherViewModel(private val weatherRepository: WeatherRepository?) : ViewModel() {

    private val _weather = MutableLiveData<Weather?>()
    val weather: LiveData<Weather?> = _weather

    private val townName = ObservableField<String>().toString()

    fun getCurrentWeather() {

        viewModelScope.launch {
            weatherRepository?.let {

                weatherRepository.getTown(townName)
                when (val infoTown =
                    weatherRepository.getTown(townName)) {
                    is NetworkState.Success -> {
                        _weather.postValue(infoTown.data)
                        Log.i("sasha", "getCurrentWeather: ${infoTown.data}")
                    }
                    is NetworkState.Error -> {
                        Log.e("ERROR", "NetworkState error")
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