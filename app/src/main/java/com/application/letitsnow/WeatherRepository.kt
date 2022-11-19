package com.application.letitsnow

import com.application.letitsnow.data.Weather
import com.application.letitsnow.network.ApiService
import com.application.letitsnow.network.NetworkState

class WeatherRepository (private val api: ApiService) {

    suspend fun getTown(town : String) : NetworkState<Weather> {
        val response = api.getTown(town = town)

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