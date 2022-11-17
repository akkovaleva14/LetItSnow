package com.application.letitsnow

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.application.letitsnow.data.Weather
import com.application.letitsnow.network.NetworkState
import kotlinx.coroutines.launch
import java.util.*
import kotlin.properties.ObservableProperty

class WeatherViewModel(private val weatherRepository: WeatherRepository?) : ViewModel() {

    private val _weatherList = MutableLiveData<List<Weather?>?>()
    val weatherList: LiveData<List<Weather?>?> = _weatherList
    val townName = ObservableField<String>().toString()


    fun getCurrentWeather() {

        viewModelScope.launch {
            weatherRepository?.let {

                weatherRepository.getTown(townName)
                when (val infoTown =
                    weatherRepository.getTown(townName.lowercase(Locale.getDefault()))) {
                    is NetworkState.Success -> {
                        _weatherList.postValue(infoTown.data)
                        Log.i("sasha", "getCurrentList: ${infoTown.data}")
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