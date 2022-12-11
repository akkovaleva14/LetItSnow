package com.application.letitsnow

import com.application.letitsnow.data.Weather
import com.application.letitsnow.network.ApiService
import com.application.letitsnow.network.NetworkState
import com.application.letitsnow.network.parseResponse

class WeatherRepository(private val api: ApiService) {

    suspend fun getTownWeather(town: String): NetworkState<Weather> {
        val response = api.getEverythingAboutTown(q = town)

        return response.parseResponse()
    }

}