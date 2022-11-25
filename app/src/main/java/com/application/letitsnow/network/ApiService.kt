package com.application.letitsnow.network

import com.application.letitsnow.data.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY: String = "f43156c76d3c46c49a3141152222511"

interface ApiService {

    @GET("v1/current.json")
    suspend fun getEverythingAboutTown(
        @Query("key") key : String = API_KEY,
        @Query("q") q : String = "Saint Petersburg",
        @Query("aqi") aqi : String = "no",
    ) : Response<Weather>

}