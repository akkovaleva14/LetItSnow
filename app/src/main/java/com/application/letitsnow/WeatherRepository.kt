package com.application.letitsnow

import com.application.letitsnow.data.Weather
import com.application.letitsnow.network.ApiService
import com.application.letitsnow.network.NetworkState

class WeatherRepository (val api: ApiService) {


    suspend fun getTemperatureOfTown(townName : String) : NetworkState<List<Weather>> {
        val response = api.getTemperatureOfTown(town = townName)


        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }

    }

}